package com.delivery.orders.infrastructure.adapter.in.web.mapper;

import com.delivery.orders.application.dto.CreateOrderCommand;
import com.delivery.orders.domain.model.*;
import com.delivery.orders.infrastructure.adapter.in.web.dto.CreateOrderRequest;
import org.springframework.stereotype.Component;

@Component
public class OrderWebMapper {
    
    public CreateOrderCommand toCommand(CreateOrderRequest request) {
        var customerId = CustomerId.of(request.customerId());
        var restaurantId = RestaurantId.of(request.restaurantId());
        var paymentMethod = PaymentMethod.valueOf(request.paymentMethod());
        
        var deliveryAddress = new DeliveryAddress(
            request.deliveryAddress().street(),
            request.deliveryAddress().number(),
            request.deliveryAddress().complement(),
            request.deliveryAddress().neighborhood(),
            request.deliveryAddress().city(),
            request.deliveryAddress().state(),
            request.deliveryAddress().zipCode(),
            request.deliveryAddress().latitude(),
            request.deliveryAddress().longitude(),
            request.deliveryAddress().reference()
        );
        
        var items = request.items().stream()
            .map(itemDto -> new OrderItem(
                itemDto.menuItemId(),
                itemDto.name(),
                itemDto.price(),
                itemDto.quantity(),
                itemDto.specialInstructions()
            ))
            .toList();
        
        return new CreateOrderCommand(
            customerId,
            restaurantId,
            deliveryAddress,
            items,
            request.deliveryFee(),
            paymentMethod,
            request.specialInstructions()
        );
    }
}

