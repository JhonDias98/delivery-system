package com.delivery.delivery.application.dto;

import com.delivery.delivery.domain.model.Location;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record AllocateDeliveryCommand(
    @NotBlank String orderId,
    @NotNull Location restaurantLocation,
    @NotNull Location deliveryAddress,
    @Positive BigDecimal estimatedDistance
) {
    public AllocateDeliveryCommand {
        if (orderId == null || orderId.isBlank()) {
            throw new IllegalArgumentException("Order ID cannot be null or blank");
        }
        if (restaurantLocation == null) {
            throw new IllegalArgumentException("Restaurant location cannot be null");
        }
        if (deliveryAddress == null) {
            throw new IllegalArgumentException("Delivery address cannot be null");
        }
        if (estimatedDistance == null || estimatedDistance.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Estimated distance must be positive");
        }
    }
}

