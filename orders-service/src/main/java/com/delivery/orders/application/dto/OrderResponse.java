package com.delivery.orders.application.dto;

import com.delivery.orders.domain.model.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record OrderResponse(
    String id,
    String customerId,
    String restaurantId,
    DeliveryAddress deliveryAddress,
    List<OrderItemDto> items,
    OrderStatus status,
    BigDecimal subtotal,
    BigDecimal deliveryFee,
    BigDecimal totalAmount,
    PaymentMethod paymentMethod,
    String specialInstructions,
    Instant createdAt,
    Instant updatedAt,
    SagaState sagaState
) {
    public static OrderResponse from(Order order) {
        var itemDtos = order.items().stream()
            .map(OrderItemDto::from)
            .toList();
        
        return new OrderResponse(
            order.id().toString(),
            order.customerId().toString(),
            order.restaurantId().toString(),
            order.deliveryAddress(),
            itemDtos,
            order.status(),
            order.subtotal(),
            order.deliveryFee(),
            order.totalAmount(),
            order.paymentMethod(),
            order.specialInstructions(),
            order.createdAt(),
            order.updatedAt(),
            order.sagaState()
        );
    }
}

