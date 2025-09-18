package com.delivery.restaurants.domain.model;

import java.math.BigDecimal;

public record MenuItem(
    MenuItemId id,
    String name,
    String description,
    BigDecimal price,
    MenuCategory category,
    boolean available,
    String imageUrl
) {
    public MenuItem {
        if (id == null) {
            throw new IllegalArgumentException("Menu item ID cannot be null");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Menu item name cannot be null or blank");
        }
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Menu item price must be positive");
        }
        if (category == null) {
            throw new IllegalArgumentException("Menu item category cannot be null");
        }
    }
    
    public MenuItem markAsUnavailable() {
        return new MenuItem(id, name, description, price, category, false, imageUrl);
    }
    
    public MenuItem markAsAvailable() {
        return new MenuItem(id, name, description, price, category, true, imageUrl);
    }
    
    public MenuItem updatePrice(BigDecimal newPrice) {
        if (newPrice == null || newPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("New price must be positive");
        }
        return new MenuItem(id, name, description, newPrice, category, available, imageUrl);
    }
}

