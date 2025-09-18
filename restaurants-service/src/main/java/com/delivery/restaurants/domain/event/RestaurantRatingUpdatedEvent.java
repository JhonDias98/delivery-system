package com.delivery.restaurants.domain.event;

import java.math.BigDecimal;
import java.time.Instant;

public record RestaurantRatingUpdatedEvent(
    String restaurantId,
    String restaurantName,
    BigDecimal newRating,
    int totalReviews,
    Instant timestamp
) {
    public RestaurantRatingUpdatedEvent {
        if (restaurantId == null || restaurantId.isBlank()) {
            throw new IllegalArgumentException("Restaurant ID cannot be null or blank");
        }
        if (restaurantName == null || restaurantName.isBlank()) {
            throw new IllegalArgumentException("Restaurant name cannot be null or blank");
        }
        if (newRating == null || newRating.compareTo(BigDecimal.ZERO) < 0 || newRating.compareTo(BigDecimal.valueOf(5)) > 0) {
            throw new IllegalArgumentException("Rating must be between 0 and 5");
        }
        if (totalReviews < 0) {
            throw new IllegalArgumentException("Total reviews cannot be negative");
        }
        if (timestamp == null) {
            timestamp = Instant.now();
        }
    }
}

