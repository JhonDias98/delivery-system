package com.delivery.orders.domain.event.saga;

import java.time.Instant;

public record RestaurantCompensationRequestedEvent(
    String orderId,
    String restaurantId,
    String reason,
    Instant timestamp
) {
    public RestaurantCompensationRequestedEvent {
        if (orderId == null || orderId.isBlank()) {
            throw new IllegalArgumentException("Order ID cannot be null or blank");
        }
        if (restaurantId == null || restaurantId.isBlank()) {
            throw new IllegalArgumentException("Restaurant ID cannot be null or blank");
        }
        if (reason == null || reason.isBlank()) {
            throw new IllegalArgumentException("Reason cannot be null or blank");
        }
        if (timestamp == null) {
            timestamp = Instant.now();
        }
    }
}

