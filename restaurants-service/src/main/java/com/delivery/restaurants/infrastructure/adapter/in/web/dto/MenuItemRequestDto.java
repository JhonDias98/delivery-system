package com.delivery.restaurants.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record MenuItemRequestDto(
    @NotBlank String name,
    @NotBlank String description,
    @NotNull @Positive BigDecimal price,
    @NotBlank String category,
    boolean available,
    String imageUrl
) {}

