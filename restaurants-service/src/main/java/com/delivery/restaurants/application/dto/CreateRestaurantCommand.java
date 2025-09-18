package com.delivery.restaurants.application.dto;

import com.delivery.restaurants.domain.model.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record CreateRestaurantCommand(
    @NotBlank String name,
    @NotBlank String description,
    @Valid @NotNull Address address,
    @Valid @NotNull ContactInfo contactInfo,
    @NotNull List<OperatingHours> operatingHours,
    @NotNull List<MenuItem> menu
) {
    public CreateRestaurantCommand {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Restaurant name cannot be null or blank");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Restaurant description cannot be null or blank");
        }
        if (address == null) {
            throw new IllegalArgumentException("Address cannot be null");
        }
        if (contactInfo == null) {
            throw new IllegalArgumentException("Contact info cannot be null");
        }
        if (operatingHours == null || operatingHours.isEmpty()) {
            throw new IllegalArgumentException("Operating hours cannot be null or empty");
        }
        if (menu == null) {
            throw new IllegalArgumentException("Menu cannot be null");
        }
    }
}

