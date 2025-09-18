package com.delivery.bff.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record OrderResponse(
    String id,
    String customerId,
    String restaurantId,
    String restaurantName,
    List<OrderItemDto> items,
    BigDecimal totalAmount,
    String status,
    DeliveryAddressDto deliveryAddress,
    PaymentInfo paymentInfo,
    DeliveryInfo deliveryInfo,
    Instant createdAt,
    Instant updatedAt
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
        String zipCode
    ) {}
    
    public record PaymentInfo(
        String paymentId,
        String status,
        String method,
        BigDecimal amount,
        Instant processedAt
    ) {}
    
    public record DeliveryInfo(
        String deliveryId,
        String status,
        String deliveryPersonName,
        String estimatedTime,
        LocationDto currentLocation
    ) {}
    
    public record LocationDto(
        double latitude,
        double longitude
    ) {}
    
    public static OrderResponse from(OrderDto order, PaymentDto payment, DeliveryDto delivery) {
        var paymentInfo = payment != null ? new PaymentInfo(
            payment.id(),
            payment.status(),
            payment.method(),
            payment.amount(),
            payment.processedAt()
        ) : null;
        
        var deliveryInfo = delivery != null ? new DeliveryInfo(
            delivery.id(),
            delivery.status(),
            delivery.deliveryPersonName(),
            delivery.estimatedDeliveryTime(),
            delivery.currentLocation() != null ? new LocationDto(
                delivery.currentLocation().latitude(),
                delivery.currentLocation().longitude()
            ) : null
        ) : null;
        
        return new OrderResponse(
            order.id(),
            order.customerId(),
            order.restaurantId(),
            order.restaurantName(),
            order.items(),
            order.totalAmount(),
            order.status(),
            order.deliveryAddress(),
            paymentInfo,
            deliveryInfo,
            order.createdAt(),
            order.updatedAt()
        );
    }
}

