package com.delivery.delivery.domain.event;

import com.delivery.delivery.domain.model.Location;

import java.time.Instant;

public record DeliveryPersonLocationUpdatedEvent(
    String deliveryPersonId,
    Location newLocation,
    Instant timestamp
) {
    
    public DeliveryPersonLocationUpdatedEvent {
        if (deliveryPersonId == null || deliveryPersonId.isBlank()) {
            throw new IllegalArgumentException("Delivery person ID cannot be null or blank");
        }
        if (newLocation == null) {
            throw new IllegalArgumentException("New location cannot be null");
        }
        if (timestamp == null) {
            throw new IllegalArgumentException("Timestamp cannot be null");
        }
    }
}

