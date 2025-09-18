package com.delivery.orders.domain.event.saga;

import com.delivery.orders.domain.model.PaymentMethod;
import java.math.BigDecimal;
import java.time.Instant;

public record PaymentProcessingRequestedEvent(
    String orderId,
    String customerId,
    BigDecimal amount,
    PaymentMethod paymentMethod,
    String deliveryPersonId,
    Instant timestamp
) {
    public PaymentProcessingRequestedEvent {
        if (orderId == null || orderId.isBlank()) {
            throw new IllegalArgumentException("Order ID cannot be null or blank");
        }
        if (customerId == null || customerId.isBlank()) {
            throw new IllegalArgumentException("Customer ID cannot be null or blank");
        }
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (paymentMethod == null) {
            throw new IllegalArgumentException("Payment method cannot be null");
        }
        if (deliveryPersonId == null || deliveryPersonId.isBlank()) {
            throw new IllegalArgumentException("Delivery person ID cannot be null or blank");
        }
        if (timestamp == null) {
            timestamp = Instant.now();
        }
    }
}

