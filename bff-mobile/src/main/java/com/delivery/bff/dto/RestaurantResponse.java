package com.delivery.bff.dto;

import java.math.BigDecimal;
import java.util.List;

public record RestaurantResponse(
    String id,
    String name,
    String description,
    String cuisine,
    AddressDto address,
    ContactInfoDto contactInfo,
    String status,
    BigDecimal rating,
    int totalReviews,
    List<MenuItemDto> menu,
    OperatingHoursDto operatingHours
) {
    
    public record AddressDto(
        String street,
        String number,
        String complement,
        String neighborhood,
        String city,
        String state,
        String zipCode,
        double latitude,
        double longitude
    ) {}
    
    public record ContactInfoDto(
        String phone,
        String email,
        String website
    ) {}
    
    public record MenuItemDto(
        String id,
        String name,
        String description,
        BigDecimal price,
        String category,
        boolean available,
        String imageUrl
    ) {}
    
    public record OperatingHoursDto(
        String openTime,
        String closeTime,
        List<String> workingDays
    ) {}
}

