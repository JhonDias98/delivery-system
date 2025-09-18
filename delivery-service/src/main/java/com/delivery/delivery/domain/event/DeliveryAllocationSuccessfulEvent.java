package com.delivery.delivery.domain.event;

import java.time.Instant;

public record DeliveryAllocationSuccessfulEvent(
    String orderId,
    String deliveryPersonId,
    String deliveryPersonName,
    Instant estimatedPickupTime,
    Instant estimatedDeliveryTime,
    Instant timestamp
) {
    
    public DeliveryAllocationSuccessfulEvent {
        if (orderId == null || orderId.isBlank()) {
            throw new IllegalArgumentException("Order ID cannot be null or blank");
        }
        if (deliveryPersonId == null || deliveryPersonId.isBlank()) {
            throw new IllegalArgumentException("Delivery person ID cannot be null or blank");
        }
        if (deliveryPersonName == null || deliveryPersonName.isBlank()) {
            throw new IllegalArgumentException("Delivery person name cannot be null or blank");
        }
        if (estimatedPickupTime == null) {
            throw new IllegalArgumentException("Estimated pickup time cannot be null");
        }
        if (estimatedDeliveryTime == null) {
            throw new IllegalArgumentException("Estimated delivery time cannot be null");
        }
        if (timestamp == null) {
            throw new IllegalArgumentException("Timestamp cannot be null");
        }
        if (estimatedDeliveryTime.isBefore(estimatedPickupTime)) {
            throw new IllegalArgumentException("Estimated delivery time cannot be before pickup time");
        }
    }
}

