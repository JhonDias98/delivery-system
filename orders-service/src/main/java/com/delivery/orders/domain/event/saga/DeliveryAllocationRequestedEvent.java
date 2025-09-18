package com.delivery.orders.domain.event.saga;

import com.delivery.orders.domain.model.DeliveryAddress;
import java.math.BigDecimal;
import java.time.Instant;

public record DeliveryAllocationRequestedEvent(
    String orderId,
    String restaurantId,
    DeliveryAddress deliveryAddress,
    BigDecimal orderValue,
    Instant timestamp
) {
    public DeliveryAllocationRequestedEvent {
        if (orderId == null || orderId.isBlank()) {
            throw new IllegalArgumentException("Order ID cannot be null or blank");
        }
        if (restaurantId == null || restaurantId.isBlank()) {
            throw new IllegalArgumentException("Restaurant ID cannot be null or blank");
        }
        if (deliveryAddress == null) {
            throw new IllegalArgumentException("Delivery address cannot be null");
        }
        if (orderValue == null || orderValue.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Order value must be positive");
        }
        if (timestamp == null) {
            timestamp = Instant.now();
        }
    }
}

