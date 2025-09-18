package com.delivery.orders.application.usecase;

import com.delivery.orders.application.dto.OrderResponse;
import com.delivery.orders.domain.exception.OrderNotFoundException;
import com.delivery.orders.domain.model.OrderId;
import com.delivery.orders.domain.port.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class GetOrderUseCase {
    
    private final OrderRepository orderRepository;
    
    public GetOrderUseCase(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
    
    public OrderResponse execute(String orderId) {
        var order = orderRepository.findById(OrderId.of(orderId))
            .orElseThrow(() -> new OrderNotFoundException("Order not found: " + orderId));
        
        return OrderResponse.from(order);
    }
}

