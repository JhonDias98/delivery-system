package com.delivery.orders.domain.event.saga;

public record OrderItemValidation(
    String menuItemId,
    int quantity
) {
    public OrderItemValidation {
        if (menuItemId == null || menuItemId.isBlank()) {
            throw new IllegalArgumentException("Menu item ID cannot be null or blank");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
    }
}

