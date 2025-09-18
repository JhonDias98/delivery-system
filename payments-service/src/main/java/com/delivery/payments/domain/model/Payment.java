package com.delivery.payments.domain.model;

import java.math.BigDecimal;
import java.time.Instant;

public record Payment(
    PaymentId id,
    String orderId,
    String customerId,
    BigDecimal amount,
    PaymentMethod method,
    PaymentStatus status,
    String transactionId,
    String gatewayResponse,
    Instant createdAt,
    Instant processedAt,
    Instant updatedAt
) {
    
    public Payment {
        if (id == null) {
            throw new IllegalArgumentException("Payment ID cannot be null");
        }
        if (orderId == null || orderId.isBlank()) {
            throw new IllegalArgumentException("Order ID cannot be null or blank");
        }
        if (customerId == null || customerId.isBlank()) {
            throw new IllegalArgumentException("Customer ID cannot be null or blank");
        }
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (method == null) {
            throw new IllegalArgumentException("Payment method cannot be null");
        }
        if (status == null) {
            throw new IllegalArgumentException("Payment status cannot be null");
        }
        if (createdAt == null) {
            createdAt = Instant.now();
        }
        if (updatedAt == null) {
            updatedAt = createdAt;
        }
    }
    
    public static Payment create(
        String orderId,
        String customerId,
        BigDecimal amount,
        PaymentMethod method
    ) {
        return new Payment(
            PaymentId.generate(),
            orderId,
            customerId,
            amount,
            method,
            PaymentStatus.PENDING,
            null,
            null,
            Instant.now(),
            null,
            Instant.now()
        );
    }
    
    public Payment process(String transactionId, String gatewayResponse) {
        if (status != PaymentStatus.PENDING) {
            throw new IllegalStateException("Can only process pending payments");
        }
        
        return new Payment(
            id, orderId, customerId, amount, method,
            PaymentStatus.APPROVED, transactionId, gatewayResponse,
            createdAt, Instant.now(), Instant.now()
        );
    }
    
    public Payment fail(String gatewayResponse) {
        if (status == PaymentStatus.APPROVED) {
            throw new IllegalStateException("Cannot fail approved payment");
        }
        
        return new Payment(
            id, orderId, customerId, amount, method,
            PaymentStatus.FAILED, transactionId, gatewayResponse,
            createdAt, processedAt, Instant.now()
        );
    }
    
    public Payment refund() {
        if (status != PaymentStatus.APPROVED) {
            throw new IllegalStateException("Can only refund approved payments");
        }
        
        return new Payment(
            id, orderId, customerId, amount, method,
            PaymentStatus.REFUNDED, transactionId, gatewayResponse,
            createdAt, processedAt, Instant.now()
        );
    }
}

