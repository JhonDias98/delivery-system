package com.delivery.orders.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record DeliveryAddressDto(
    @NotBlank String street,
    @NotBlank String number,
    String complement,
    @NotBlank String neighborhood,
    @NotBlank String city,
    @NotBlank String state,
    @NotBlank String zipCode,
    BigDecimal latitude,
    BigDecimal longitude,
    String reference
) {}

