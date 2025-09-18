package com.delivery.delivery.domain.model;

import java.util.UUID;

public record DeliveryPersonId(UUID value) {
    public DeliveryPersonId {
        if (value == null) {
            throw new IllegalArgumentException("Delivery person ID cannot be null");
        }
    }
    
    public static DeliveryPersonId generate() {
        return new DeliveryPersonId(UUID.randomUUID());
    }
    
    public static DeliveryPersonId of(String value) {
        return new DeliveryPersonId(UUID.fromString(value));
    }
    
    @Override
    public String toString() {
        return value.toString();
    }
}

