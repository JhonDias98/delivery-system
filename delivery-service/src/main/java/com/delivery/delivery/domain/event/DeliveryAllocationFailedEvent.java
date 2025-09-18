package com.delivery.delivery.domain.event;

import java.time.Instant;

public record DeliveryAllocationFailedEvent(
    String orderId,
    String reason,
    Instant timestamp
) {
    
    public DeliveryAllocationFailedEvent {
        if (orderId == null || orderId.isBlank()) {
            throw new IllegalArgumentException("Order ID cannot be null or blank");
        }
        if (reason == null || reason.isBlank()) {
            throw new IllegalArgumentException("Reason cannot be null or blank");
        }
        if (timestamp == null) {
            throw new IllegalArgumentException("Timestamp cannot be null");
        }
    }
}

