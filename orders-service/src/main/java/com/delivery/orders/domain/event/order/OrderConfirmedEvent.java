package com.delivery.orders.domain.event.order;

import java.math.BigDecimal;
import java.time.Instant;

public record OrderConfirmedEvent(
    String orderId,
    String customerId,
    String restaurantId,
    String transactionId,
    BigDecimal totalAmount,
    Instant timestamp
) {
    public OrderConfirmedEvent {
        if (orderId == null || orderId.isBlank()) {
            throw new IllegalArgumentException("Order ID cannot be null or blank");
        }
        if (customerId == null || customerId.isBlank()) {
            throw new IllegalArgumentException("Customer ID cannot be null or blank");
        }
        if (restaurantId == null || restaurantId.isBlank()) {
            throw new IllegalArgumentException("Restaurant ID cannot be null or blank");
        }
        if (transactionId == null || transactionId.isBlank()) {
            throw new IllegalArgumentException("Transaction ID cannot be null or blank");
        }
        if (totalAmount == null || totalAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Total amount must be positive");
        }
        if (timestamp == null) {
            timestamp = Instant.now();
        }
    }
}

