package com.delivery.orders.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record OrderItemRequestDto(
    @NotBlank String menuItemId,
    @NotBlank String name,
    @NotNull @Positive BigDecimal price,
    @Positive int quantity,
    String specialInstructions
) {}

