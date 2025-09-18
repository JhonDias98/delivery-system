package com.delivery.restaurants.domain.event;

import java.time.Instant;
import java.util.List;

public record MenuUpdatedEvent(
    String restaurantId,
    String restaurantName,
    List<String> updatedMenuItemIds,
    Instant timestamp
) {
    public MenuUpdatedEvent {
        if (restaurantId == null || restaurantId.isBlank()) {
            throw new IllegalArgumentException("Restaurant ID cannot be null or blank");
        }
        if (restaurantName == null || restaurantName.isBlank()) {
            throw new IllegalArgumentException("Restaurant name cannot be null or blank");
        }
        if (updatedMenuItemIds == null) {
            updatedMenuItemIds = List.of();
        }
        if (timestamp == null) {
            timestamp = Instant.now();
        }
    }
}

