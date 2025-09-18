package com.delivery.delivery.domain.valueobject;

import com.delivery.delivery.domain.model.Location;
import com.delivery.delivery.domain.model.VehicleInfo;

import java.time.Instant;

public record DeliveryAllocation(
    String deliveryId,
    String deliveryPersonId,
    String deliveryPersonName,
    VehicleInfo vehicle,
    Location currentLocation,
    Instant estimatedPickupTime,
    Instant estimatedDeliveryTime
) {
    
    public DeliveryAllocation {
        if (deliveryId == null || deliveryId.isBlank()) {
            throw new IllegalArgumentException("Delivery ID cannot be null or blank");
        }
        if (deliveryPersonId == null || deliveryPersonId.isBlank()) {
            throw new IllegalArgumentException("Delivery person ID cannot be null or blank");
        }
        if (deliveryPersonName == null || deliveryPersonName.isBlank()) {
            throw new IllegalArgumentException("Delivery person name cannot be null or blank");
        }
        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle cannot be null");
        }
        if (currentLocation == null) {
            throw new IllegalArgumentException("Current location cannot be null");
        }
        if (estimatedPickupTime == null) {
            throw new IllegalArgumentException("Estimated pickup time cannot be null");
        }
        if (estimatedDeliveryTime == null) {
            throw new IllegalArgumentException("Estimated delivery time cannot be null");
        }
        if (estimatedDeliveryTime.isBefore(estimatedPickupTime)) {
            throw new IllegalArgumentException("Estimated delivery time cannot be before pickup time");
        }
    }
}

