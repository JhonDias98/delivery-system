package com.delivery.delivery.domain.model;

import java.util.UUID;

public record DeliveryId(UUID value) {
    public DeliveryId {
        if (value == null) {
            throw new IllegalArgumentException("Delivery ID cannot be null");
        }
    }
    
    public static DeliveryId generate() {
        return new DeliveryId(UUID.randomUUID());
    }
    
    public static DeliveryId of(String value) {
        return new DeliveryId(UUID.fromString(value));
    }
    
    @Override
    public String toString() {
        return value.toString();
    }
}

