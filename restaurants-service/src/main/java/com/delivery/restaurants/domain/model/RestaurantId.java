package com.delivery.restaurants.domain.model;

import java.util.UUID;

public record RestaurantId(UUID value) {
    public RestaurantId {
        if (value == null) {
            throw new IllegalArgumentException("Restaurant ID cannot be null");
        }
    }
    
    public static RestaurantId generate() {
        return new RestaurantId(UUID.randomUUID());
    }
    
    public static RestaurantId of(String value) {
        return new RestaurantId(UUID.fromString(value));
    }
    
    @Override
    public String toString() {
        return value.toString();
    }
}

