package com.delivery.orders.domain.event.saga;

import java.time.Instant;
import java.util.List;

public record RestaurantValidationRequestedEvent(
    String orderId,
    String restaurantId,
    List<OrderItemValidation> items,
    Instant timestamp
) {
    public RestaurantValidationRequestedEvent {
        if (orderId == null || orderId.isBlank()) {
            throw new IllegalArgumentException("Order ID cannot be null or blank");
        }
        if (restaurantId == null || restaurantId.isBlank()) {
            throw new IllegalArgumentException("Restaurant ID cannot be null or blank");
        }
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Items cannot be null or empty");
        }
        if (timestamp == null) {
            timestamp = Instant.now();
        }
    }
}

