package com.delivery.restaurants.domain.event;

import java.time.Instant;

public record RestaurantActivatedEvent(
    String restaurantId,
    String name,
    Instant timestamp
) {
    public RestaurantActivatedEvent {
        if (restaurantId == null || restaurantId.isBlank()) {
            throw new IllegalArgumentException("Restaurant ID cannot be null or blank");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Restaurant name cannot be null or blank");
        }
        if (timestamp == null) {
            timestamp = Instant.now();
        }
    }
}

