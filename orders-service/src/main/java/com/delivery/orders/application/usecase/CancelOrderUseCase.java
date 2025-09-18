package com.delivery.orders.application.usecase;

import com.delivery.orders.application.dto.OrderResponse;
import com.delivery.orders.domain.exception.OrderNotFoundException;
import com.delivery.orders.domain.model.OrderId;
import com.delivery.orders.domain.model.OrderStatus;
import com.delivery.orders.domain.port.OrderRepository;
import com.delivery.orders.domain.service.OrderSagaOrchestrator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CancelOrderUseCase {
    
    private final OrderRepository orderRepository;
    private final OrderSagaOrchestrator sagaOrchestrator;
    
    public CancelOrderUseCase(
        OrderRepository orderRepository,
        OrderSagaOrchestrator sagaOrchestrator
    ) {
        this.orderRepository = orderRepository;
        this.sagaOrchestrator = sagaOrchestrator;
    }
    
    public OrderResponse execute(String orderId) {
        var order = orderRepository.findById(OrderId.of(orderId))
            .orElseThrow(() -> new OrderNotFoundException("Order not found: " + orderId));

        var cancelledOrder = order.updateStatus(OrderStatus.CANCELLED);
        var savedOrder = orderRepository.save(cancelledOrder);
        
        // TODO: Implementar compensação
        // if (savedOrder.requiresCompensation()) {
        //     sagaOrchestrator.compensateOrderSaga(savedOrder);
        // }
        
        return OrderResponse.from(savedOrder);
    }
}

