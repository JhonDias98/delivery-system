package com.delivery.orders.infrastructure.adapter.in.web.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.util.List;

public record CreateOrderRequest(
    @NotBlank String customerId,
    @NotBlank String restaurantId,
    @Valid @NotNull DeliveryAddressDto deliveryAddress,
    @NotEmpty List<OrderItemRequestDto> items,
    @NotNull BigDecimal deliveryFee,
    @NotBlank String paymentMethod,
    String specialInstructions
) {}

