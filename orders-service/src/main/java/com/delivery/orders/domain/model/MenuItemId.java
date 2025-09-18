package com.delivery.orders.domain.model;

import java.util.UUID;

public record MenuItemId(UUID value) {
    
    public MenuItemId {
        if (value == null) {
            throw new IllegalArgumentException("Menu item ID cannot be null");
        }
    }
    
    public static MenuItemId generate() {
        return new MenuItemId(UUID.randomUUID());
    }
    
    public static MenuItemId of(String value) {
        return new MenuItemId(UUID.fromString(value));
    }
    
    @Override
    public String toString() {
        return value.toString();
    }
}

