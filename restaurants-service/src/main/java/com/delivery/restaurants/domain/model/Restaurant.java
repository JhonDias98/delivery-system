package com.delivery.restaurants.domain.model;

import java.math.BigDecimal;
import java.util.List;

public record Restaurant(
    RestaurantId id,
    String name,
    String description,
    Address address,
    ContactInfo contactInfo,
    OperatingHours operatingHours,
    RestaurantStatus status,
    List<MenuItem> menu,
    BigDecimal averageRating,
    int totalReviews
) {
    
    public Restaurant {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Restaurant name cannot be null or blank");
        }
        if (address == null) {
            throw new IllegalArgumentException("Restaurant address cannot be null");
        }
        if (operatingHours == null) {
            throw new IllegalArgumentException("Operating hours cannot be null");
        }
        if (status == null) {
            throw new IllegalArgumentException("Restaurant status cannot be null");
        }
        if (menu == null) {
            menu = List.of();
        }
        if (averageRating != null && (averageRating.compareTo(BigDecimal.ZERO) < 0 || averageRating.compareTo(BigDecimal.valueOf(5)) > 0)) {
            throw new IllegalArgumentException("Average rating must be between 0 and 5");
        }
        if (totalReviews < 0) {
            throw new IllegalArgumentException("Total reviews cannot be negative");
        }
    }
    
    public static Restaurant create(
        String name,
        String description,
        Address address,
        ContactInfo contactInfo,
        OperatingHours operatingHours
    ) {
        return new Restaurant(
            RestaurantId.generate(),
            name,
            description,
            address,
            contactInfo,
            operatingHours,
            RestaurantStatus.PENDING_APPROVAL,
            List.of(),
            null,
            0
        );
    }
    
    public Restaurant activate() {
        if (status == RestaurantStatus.PENDING_APPROVAL) {
            return new Restaurant(id, name, description, address, contactInfo, 
                               operatingHours, RestaurantStatus.ACTIVE, menu, 
                               averageRating, totalReviews);
        }
        throw new IllegalStateException("Restaurant must be pending approval to be activated");
    }
    
    public Restaurant suspend() {
        if (status == RestaurantStatus.ACTIVE) {
            return new Restaurant(id, name, description, address, contactInfo, 
                               operatingHours, RestaurantStatus.SUSPENDED, menu, 
                               averageRating, totalReviews);
        }
        throw new IllegalStateException("Only active restaurants can be suspended");
    }
    
    public Restaurant deactivate() {
        if (status == RestaurantStatus.ACTIVE) {
            return new Restaurant(id, name, description, address, contactInfo, 
                               operatingHours, RestaurantStatus.INACTIVE, menu, 
                               averageRating, totalReviews);
        }
        throw new IllegalStateException("Only active restaurants can be deactivated");
    }
    
    public Restaurant addMenuItem(MenuItem menuItem) {
        var updatedMenu = new java.util.ArrayList<>(menu);
        updatedMenu.add(menuItem);
        
        return new Restaurant(id, name, description, address, contactInfo, 
                           operatingHours, status, List.copyOf(updatedMenu), 
                           averageRating, totalReviews);
    }
    
    public Restaurant removeMenuItem(MenuItemId menuItemId) {
        var updatedMenu = menu.stream()
            .filter(item -> !item.id().equals(menuItemId))
            .toList();
        
        return new Restaurant(id, name, description, address, contactInfo, 
                           operatingHours, status, updatedMenu, 
                           averageRating, totalReviews);
    }
    
    public Restaurant updateMenu(List<MenuItem> newMenu) {
        return new Restaurant(id, name, description, address, contactInfo, 
                           operatingHours, status, List.copyOf(newMenu), 
                           averageRating, totalReviews);
    }
    
    public Restaurant updateRating(BigDecimal newRating) {
        if (newRating == null || newRating.compareTo(BigDecimal.ZERO) < 0 || newRating.compareTo(BigDecimal.valueOf(5)) > 0) {
            throw new IllegalArgumentException("Rating must be between 0 and 5");
        }
        
        return new Restaurant(id, name, description, address, contactInfo, 
                           operatingHours, status, menu, 
                           newRating, totalReviews + 1);
    }
    
    public boolean isOpen() {
        return status == RestaurantStatus.ACTIVE && operatingHours.isOpenNow();
    }
    
    public boolean hasMenuItem(MenuItemId menuItemId) {
        return menu.stream().anyMatch(item -> item.id().equals(menuItemId));
    }
}

