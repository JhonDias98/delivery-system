package com.delivery.delivery.domain.model;

import java.util.UUID;

public record DeliveryEventId(UUID value) {
    public DeliveryEventId {
        if (value == null) {
            throw new IllegalArgumentException("Event ID cannot be null");
        }
    }
    
    public static DeliveryEventId generate() {
        return new DeliveryEventId(UUID.randomUUID());
    }
    
    @Override
    public String toString() {
        return value.toString();
    }
}

