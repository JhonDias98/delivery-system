package com.delivery.restaurants.domain.event;

import java.time.Instant;

public record RestaurantDeactivatedEvent(
    String restaurantId,
    String name,
    String reason,
    Instant timestamp
) {
    public RestaurantDeactivatedEvent {
        if (restaurantId == null || restaurantId.isBlank()) {
            throw new IllegalArgumentException("Restaurant ID cannot be null or blank");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Restaurant name cannot be null or blank");
        }
        if (reason == null || reason.isBlank()) {
            throw new IllegalArgumentException("Reason cannot be null or blank");
        }
        if (timestamp == null) {
            timestamp = Instant.now();
        }
    }
}

