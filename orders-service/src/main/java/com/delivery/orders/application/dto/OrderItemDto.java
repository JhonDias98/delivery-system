package com.delivery.orders.application.dto;

import com.delivery.orders.domain.model.OrderItem;

import java.math.BigDecimal;

public record OrderItemDto(
    String menuItemId,
    String name,
    BigDecimal price,
    int quantity,
    String specialInstructions
) {
    public static OrderItemDto from(OrderItem item) {
        return new OrderItemDto(
            item.menuItemId().toString(),
            item.name(),
            item.price(),
            item.quantity(),
            item.specialInstructions()
        );
    }
}

