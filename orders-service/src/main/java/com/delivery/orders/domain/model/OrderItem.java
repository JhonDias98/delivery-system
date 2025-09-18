package com.delivery.orders.domain.model;

import java.math.BigDecimal;

public record OrderItem(
    String menuItemId,
    String name,
    BigDecimal price,
    int quantity,
    String specialInstructions
) {
    public OrderItem {
        if (menuItemId == null || menuItemId.isBlank()) {
            throw new IllegalArgumentException("Menu item ID cannot be null or blank");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Item name cannot be null or blank");
        }
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Item price must be positive");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
    }
    
    public BigDecimal getTotalPrice() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }
}

