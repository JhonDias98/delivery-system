package com.delivery.orders.application.usecase;

import com.delivery.orders.application.dto.CreateOrderCommand;
import com.delivery.orders.application.dto.OrderResponse;
import com.delivery.orders.domain.model.Order;
import com.delivery.orders.domain.port.OrderRepository;
import com.delivery.orders.domain.service.OrderSagaOrchestrator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CreateOrderUseCase {
    
    private final OrderRepository orderRepository;
    private final OrderSagaOrchestrator sagaOrchestrator;
    
    public CreateOrderUseCase(
        OrderRepository orderRepository,
        OrderSagaOrchestrator sagaOrchestrator
    ) {
        this.orderRepository = orderRepository;
        this.sagaOrchestrator = sagaOrchestrator;
    }
    
    public OrderResponse execute(CreateOrderCommand command) {
        var order = Order.create(
            command.customerId(),
            command.restaurantId(),
            command.deliveryAddress(),
            command.items(),
            command.deliveryFee(),
            command.paymentMethod(),
            command.specialInstructions()
        );
        
        var savedOrder = orderRepository.save(order);
        
        sagaOrchestrator.startOrderSaga(savedOrder);
        
        return OrderResponse.from(savedOrder);
    }
}

