package com.delivery.orders.domain.event.saga;

import java.time.Instant;

public record DeliveryCompensationRequestedEvent(
    String orderId,
    String deliveryPersonId,
    String reason,
    Instant timestamp
) {
    public DeliveryCompensationRequestedEvent {
        if (orderId == null || orderId.isBlank()) {
            throw new IllegalArgumentException("Order ID cannot be null or blank");
        }
        if (deliveryPersonId == null || deliveryPersonId.isBlank()) {
            throw new IllegalArgumentException("Delivery person ID cannot be null or blank");
        }
        if (reason == null || reason.isBlank()) {
            throw new IllegalArgumentException("Reason cannot be null or blank");
        }
        if (timestamp == null) {
            timestamp = Instant.now();
        }
    }
}

