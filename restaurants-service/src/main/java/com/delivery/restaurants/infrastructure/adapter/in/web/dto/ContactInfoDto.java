package com.delivery.restaurants.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;

public record ContactInfoDto(
    @NotBlank String phone,
    @Email String email,
    String website
) {}

