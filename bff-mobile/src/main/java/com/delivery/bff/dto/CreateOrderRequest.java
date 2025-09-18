package com.delivery.bff.dto;

import java.math.BigDecimal;
import java.util.List;

public record CreateOrderRequest(
    String customerId,
    String restaurantId,
    List<OrderItemDto> items,
    DeliveryAddressDto deliveryAddress,
    String paymentMethod
) {
    
    public record OrderItemDto(
        String menuItemId,
        String name,
        BigDecimal price,
        int quantity,
        String specialInstructions
    ) {}
    
    public record DeliveryAddressDto(
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
}

