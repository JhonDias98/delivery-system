package com.delivery.restaurants.domain.event;

import java.time.Instant;

public record RestaurantCreatedEvent(
    String restaurantId,
    String name,
    String address,
    Instant timestamp
) {
    public RestaurantCreatedEvent {
        if (restaurantId == null || restaurantId.isBlank()) {
            throw new IllegalArgumentException("Restaurant ID cannot be null or blank");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Restaurant name cannot be null or blank");
        }
        if (address == null || address.isBlank()) {
            throw new IllegalArgumentException("Address cannot be null or blank");
        }
        if (timestamp == null) {
            timestamp = Instant.now();
        }
    }
}

