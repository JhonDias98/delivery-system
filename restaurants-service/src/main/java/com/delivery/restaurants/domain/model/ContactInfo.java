package com.delivery.restaurants.domain.model;

public record ContactInfo(
    String phone,
    String email,
    String website
) {
    public ContactInfo {
        if (phone == null || phone.isBlank()) {
            throw new IllegalArgumentException("Phone cannot be null or blank");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be null or blank");
        }
    }
}

