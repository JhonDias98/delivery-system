package com.delivery.payments.domain.service;

import com.delivery.payments.domain.event.*;
import com.delivery.payments.domain.exception.PaymentNotFoundException;
import com.delivery.payments.domain.exception.PaymentProcessingException;
import com.delivery.payments.domain.model.*;
import com.delivery.payments.domain.port.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class PaymentService {
    
    private final PaymentRepository paymentRepository;
    private final PaymentGateway paymentGateway;
    private final EventPublisher eventPublisher;
    private final CacheService cacheService;
    
    public PaymentService(
        PaymentRepository paymentRepository,
        PaymentGateway paymentGateway,
        EventPublisher eventPublisher,
        CacheService cacheService
    ) {
        this.paymentRepository = paymentRepository;
        this.paymentGateway = paymentGateway;
        this.eventPublisher = eventPublisher;
        this.cacheService = cacheService;
    }
    

    public Payment processPayment(
        String orderId,
        String customerId,
        BigDecimal amount,
        PaymentMethod method
    ) {
        var payment = Payment.create(orderId, customerId, amount, method);
        
        var savedPayment = paymentRepository.save(payment);
        
        publishPaymentCreated(savedPayment);
        
        try {
            var gatewayResponse = paymentGateway.processPayment(
                savedPayment.id().toString(),
                amount,
                method
            );
            
            var processedPayment = savedPayment.process(
                gatewayResponse.transactionId(),
                gatewayResponse.response()
            );
            
            var finalPayment = paymentRepository.save(processedPayment);
            
            cacheService.cachePayment(finalPayment);
            
            publishPaymentApproved(finalPayment);
            
            return finalPayment;
            
        } catch (Exception e) {
            var failedPayment = savedPayment.fail("Gateway error: " + e.getMessage());
            var finalPayment = paymentRepository.save(failedPayment);
            
            cacheService.cachePayment(finalPayment);
            
            publishPaymentFailed(finalPayment, e.getMessage());

            throw new PaymentProcessingException("Payment processing failed", e);
        }
    }

    public Payment refundPayment(PaymentId paymentId, String reason) {
        var payment = paymentRepository.findById(paymentId)
            .orElseThrow(() -> new PaymentNotFoundException("Payment not found: " + paymentId));
        
        if (!payment.status().canBeRefunded()) {
            throw new IllegalStateException("Payment cannot be refunded in current status: " + payment.status());
        }
        
        try {
            var refundResponse = paymentGateway.refundPayment(
                payment.transactionId(),
                payment.amount()
            );
            
            var refundedPayment = payment.refund();
            var savedPayment = paymentRepository.save(refundedPayment);
            
            cacheService.cachePayment(savedPayment);
            
            publishPaymentRefunded(savedPayment, reason);
            
            return savedPayment;
            
        } catch (Exception e) {
            throw new PaymentProcessingException("Refund processing failed", e);
        }
    }
    
    public Optional<Payment> findByOrderId(String orderId) {
        var cached = cacheService.getCachedPaymentByOrderId(orderId);
        if (cached.isPresent()) {
            return cached;
        }
        
        var payment = paymentRepository.findByOrderId(orderId);
        
        payment.ifPresent(cacheService::cachePayment);
        
        return payment;
    }

    public List<Payment> findByCustomerId(String customerId) {
        return paymentRepository.findByCustomerId(customerId);
    }

    public List<Payment> findByStatus(PaymentStatus status) {
        return paymentRepository.findByStatus(status);
    }
    
    private void publishPaymentCreated(Payment payment) {
        var event = new PaymentCreatedEvent(
            payment.id().toString(),
            payment.orderId(),
            payment.customerId(),
            payment.amount(),
            payment.method(),
            payment.createdAt()
        );
        eventPublisher.publishPaymentCreated(event);
    }
    
    private void publishPaymentApproved(Payment payment) {
        var event = new PaymentApprovedEvent(
            payment.id().toString(),
            payment.orderId(),
            payment.customerId(),
            payment.amount(),
            payment.transactionId(),
            payment.processedAt()
        );
        eventPublisher.publishPaymentApproved(event);
    }
    
    private void publishPaymentFailed(Payment payment, String reason) {
        var event = new PaymentFailedEvent(
            payment.id().toString(),
            payment.orderId(),
            payment.customerId(),
            payment.amount(),
            reason,
            payment.updatedAt()
        );
        eventPublisher.publishPaymentFailed(event);
    }
    
    private void publishPaymentRefunded(Payment payment, String reason) {
        var event = new PaymentRefundedEvent(
            payment.id().toString(),
            payment.orderId(),
            payment.customerId(),
            payment.amount(),
            reason,
            payment.updatedAt()
        );
        eventPublisher.publishPaymentRefunded(event);
    }
}

