package com.delivery.bff.dto;

import java.time.Instant;

public record DeliveryDto(
    String id,
    String orderId,
    String deliveryPersonId,
    String deliveryPersonName,
    String status,
    LocationDto currentLocation,
    String estimatedDeliveryTime,
    Instant createdAt,
    Instant updatedAt
) {
    
    public record LocationDto(
        double latitude,
        double longitude
    ) {}
}

