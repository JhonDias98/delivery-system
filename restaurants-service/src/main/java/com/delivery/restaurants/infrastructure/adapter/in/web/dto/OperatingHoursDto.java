package com.delivery.restaurants.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

public record OperatingHoursDto(
    @NotNull String dayOfWeek,
    @NotNull LocalTime openTime,
    @NotNull LocalTime closeTime,
    boolean closed
) {}

