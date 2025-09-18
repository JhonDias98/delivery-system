package com.delivery.delivery.domain.model;

public record VehicleInfo(
    VehicleType type,
    String licensePlate,
    String model,
    String color
) {
    public VehicleInfo {
        if (type == null) {
            throw new IllegalArgumentException("Vehicle type cannot be null");
        }
        if (licensePlate == null || licensePlate.isBlank()) {
            throw new IllegalArgumentException("License plate cannot be null or blank");
        }
    }
}

