package com.delivery.orders.application.dto;

import com.delivery.orders.domain.model.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;

import java.math.BigDecimal;
import java.util.List;

public record CreateOrderCommand(
    @NotNull CustomerId customerId,
    @NotNull RestaurantId restaurantId,
    @Valid @NotNull DeliveryAddress deliveryAddress,
    @NotEmpty List<OrderItem> items,
    @NotNull BigDecimal deliveryFee,
    @NotNull PaymentMethod paymentMethod,
    String specialInstructions
) {
    public CreateOrderCommand {
        if (customerId == null) {
            throw new IllegalArgumentException("Customer ID cannot be null");
        }
        if (restaurantId == null) {
            throw new IllegalArgumentException("Restaurant ID cannot be null");
        }
        if (deliveryAddress == null) {
            throw new IllegalArgumentException("Delivery address cannot be null");
        }
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Items cannot be null or empty");
        }
        if (deliveryFee == null || deliveryFee.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Delivery fee cannot be null or negative");
        }
        if (paymentMethod == null) {
            throw new IllegalArgumentException("Payment method cannot be null");
        }
    }
}

