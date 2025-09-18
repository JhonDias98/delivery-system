package com.delivery.bff.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record OrderDto(
    String id,
    String customerId,
    String restaurantId,
    String restaurantName,
    List<OrderResponse.OrderItemDto> items,
    BigDecimal totalAmount,
    String status,
    OrderResponse.DeliveryAddressDto deliveryAddress,
    Instant createdAt,
    Instant updatedAt
) {}

