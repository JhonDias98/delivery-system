package com.delivery.payments.domain.event;

import com.delivery.payments.domain.model.PaymentMethod;

import java.math.BigDecimal;
import java.time.Instant;

public record PaymentCreatedEvent(
    String paymentId,
    String orderId,
    String customerId,
    BigDecimal amount,
    PaymentMethod method,
    Instant timestamp
) {
    
    public PaymentCreatedEvent {
        if (paymentId == null || paymentId.isBlank()) {
            throw new IllegalArgumentException("Payment ID cannot be null or blank");
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
        if (timestamp == null) {
            throw new IllegalArgumentException("Timestamp cannot be null");
        }
    }
}

