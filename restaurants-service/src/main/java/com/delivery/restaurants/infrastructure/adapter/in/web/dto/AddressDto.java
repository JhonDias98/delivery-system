package com.delivery.restaurants.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.NotBlank;

public record AddressDto(
    @NotBlank String street,
    @NotBlank String number,
    String complement,
    @NotBlank String neighborhood,
    @NotBlank String city,
    @NotBlank String state,
    @NotBlank String zipCode
) {}

