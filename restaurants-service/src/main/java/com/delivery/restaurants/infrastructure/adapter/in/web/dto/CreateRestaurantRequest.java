package com.delivery.restaurants.infrastructure.adapter.in.web.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record CreateRestaurantRequest(
    @NotBlank String name,
    @NotBlank String description,
    @Valid @NotNull AddressDto address,
    @Valid @NotNull ContactInfoDto contactInfo,
    @NotNull List<OperatingHoursDto> operatingHours,
    List<MenuItemRequestDto> menu
) {}

