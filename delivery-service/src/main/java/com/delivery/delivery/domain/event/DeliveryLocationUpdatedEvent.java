package com.delivery.delivery.domain.event;

import com.delivery.delivery.domain.model.DeliveryStatus;
import com.delivery.delivery.domain.model.Location;

import java.time.Instant;

public record DeliveryLocationUpdatedEvent(
    String deliveryId,
    String orderId,
    Location newLocation,
    DeliveryStatus status,
    Instant timestamp
) {
    
    public DeliveryLocationUpdatedEvent {
        if (deliveryId == null || deliveryId.isBlank()) {
            throw new IllegalArgumentException("Delivery ID cannot be null or blank");
        }
        if (orderId == null || orderId.isBlank()) {
            throw new IllegalArgumentException("Order ID cannot be null or blank");
        }
        if (newLocation == null) {
            throw new IllegalArgumentException("New location cannot be null");
        }
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }
        if (timestamp == null) {
            throw new IllegalArgumentException("Timestamp cannot be null");
        }
    }
}

