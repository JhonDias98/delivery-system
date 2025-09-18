package com.delivery.orders.domain.event.order;

import java.time.Instant;

public record OrderSagaFailedEvent(
    String orderId,
    String reason,
    Instant timestamp
) {
    public OrderSagaFailedEvent {
        if (orderId == null || orderId.isBlank()) {
            throw new IllegalArgumentException("Order ID cannot be null or blank");
        }
        if (reason == null || reason.isBlank()) {
            throw new IllegalArgumentException("Reason cannot be null or blank");
        }
        if (timestamp == null) {
            timestamp = Instant.now();
        }
    }
}

