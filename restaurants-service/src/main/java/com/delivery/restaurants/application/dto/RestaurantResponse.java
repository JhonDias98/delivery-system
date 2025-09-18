package com.delivery.restaurants.application.dto;

import com.delivery.restaurants.domain.model.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record RestaurantResponse(
    String id,
    String name,
    String description,
    Address address,
    ContactInfo contactInfo,
    RestaurantStatus status,
    BigDecimal rating,
    int totalReviews,
    List<OperatingHours> operatingHours,
    List<MenuItemDto> menu,
    Instant createdAt,
    Instant updatedAt
) {
    public static RestaurantResponse from(Restaurant restaurant) {
        var menuDtos = restaurant.menu().stream()
            .map(MenuItemDto::from)
            .toList();
        
        return new RestaurantResponse(
            restaurant.id().toString(),
            restaurant.name(),
            restaurant.description(),
            restaurant.address(),
            restaurant.contactInfo(),
            restaurant.status(),
            restaurant.averageRating(),
            restaurant.totalReviews(),
            List.of(restaurant.operatingHours()),
            menuDtos,
            null,
            null
        );
    }
}

