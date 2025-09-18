package com.delivery.orders.domain.service;

import com.delivery.orders.domain.model.*;
import com.delivery.orders.domain.port.*;
import com.delivery.orders.domain.exception.OrderNotFoundException;
import com.delivery.orders.domain.event.saga.*;
import com.delivery.orders.domain.event.order.*;

import java.time.Instant;

public class OrderSagaOrchestrator {
    
    private final OrderRepository orderRepository;
    private final SagaEventPublisher eventPublisher;
    private final CacheService cacheService;
    
    public OrderSagaOrchestrator(
        OrderRepository orderRepository,
        SagaEventPublisher eventPublisher,
        CacheService cacheService
    ) {
        this.orderRepository = orderRepository;
        this.eventPublisher = eventPublisher;
        this.cacheService = cacheService;
    }

    public void startOrderSaga(Order order) {
        var sagaStartedOrder = order.startSaga();
        var savedOrder = orderRepository.save(sagaStartedOrder);
        
        cacheService.cacheOrder(savedOrder);
        
        var event = new RestaurantValidationRequestedEvent(
            savedOrder.id().toString(),
            savedOrder.restaurantId().toString(),
            savedOrder.items().stream()
                .map(item -> new OrderItemValidation(
                    item.menuItemId(),
                    item.quantity()
                ))
                .toList(),
            Instant.now()
        );
        
        eventPublisher.publishRestaurantValidationRequested(event);
    }

    public void handleRestaurantValidated(String orderId) {
        var order = findOrderById(OrderId.of(orderId));
        var validatedOrder = order.restaurantValidated();
        var savedOrder = orderRepository.save(validatedOrder);
        
        cacheService.cacheOrder(savedOrder);
        
        var event = new DeliveryAllocationRequestedEvent(
            savedOrder.id().toString(),
            savedOrder.restaurantId().toString(),
            savedOrder.deliveryAddress(),
            savedOrder.totalAmount(),
            Instant.now()
        );
        
        eventPublisher.publishDeliveryAllocationRequested(event);
    }

    public void handleRestaurantValidationFailed(String orderId, String reason) {
        var order = findOrderById(OrderId.of(orderId));
        var failedOrder = order.sagaFailed();
        var savedOrder = orderRepository.save(failedOrder);
        
        cacheService.cacheOrder(savedOrder);
        
        var event = new OrderSagaFailedEvent(
            savedOrder.id().toString(),
            "Restaurant validation failed: " + reason,
            Instant.now()
        );
        
        eventPublisher.publishOrderSagaFailed(event);
    }

    public void handleDeliveryAllocated(String orderId, String deliveryPersonId) {
        var order = findOrderById(OrderId.of(orderId));
        var allocatedOrder = order.deliveryAllocated();
        var savedOrder = orderRepository.save(allocatedOrder);
        
        cacheService.cacheOrder(savedOrder);
        
        var event = new PaymentProcessingRequestedEvent(
            savedOrder.id().toString(),
            savedOrder.customerId().toString(),
            savedOrder.totalAmount(),
            savedOrder.paymentMethod(),
            deliveryPersonId,
            Instant.now()
        );
        
        eventPublisher.publishPaymentProcessingRequested(event);
    }

    public void handleDeliveryAllocationFailed(String orderId, String reason) {
        var order = findOrderById(OrderId.of(orderId));
        
        var compensatingOrder = order.compensateRestaurant();
        var savedOrder = orderRepository.save(compensatingOrder);
        
        cacheService.cacheOrder(savedOrder);
        
        var event = new RestaurantCompensationRequestedEvent(
            savedOrder.id().toString(),
            savedOrder.restaurantId().toString(),
            "Delivery allocation failed: " + reason,
            Instant.now()
        );
        
        eventPublisher.publishRestaurantCompensationRequested(event);
    }

    public void handlePaymentProcessed(String orderId, String transactionId) {
        var order = findOrderById(OrderId.of(orderId));
        var paidOrder = order.paymentProcessed();
        var savedOrder = orderRepository.save(paidOrder);
        
        cacheService.cacheOrder(savedOrder);
        
        var event = new OrderConfirmedEvent(
            savedOrder.id().toString(),
            savedOrder.customerId().toString(),
            savedOrder.restaurantId().toString(),
            transactionId,
            savedOrder.totalAmount(),
            Instant.now()
        );
        
        eventPublisher.publishOrderConfirmed(event);
    }

    public void handlePaymentFailed(String orderId, String reason) {
        var order = findOrderById(OrderId.of(orderId));
        
        var compensatingOrder = order.compensateDelivery();
        var savedOrder = orderRepository.save(compensatingOrder);

        cacheService.cacheOrder(savedOrder);
        
        var event = new DeliveryCompensationRequestedEvent(
            savedOrder.id().toString(),
            "delivery-person-id",
            "Payment failed: " + reason,
            Instant.now()
        );
        
        eventPublisher.publishDeliveryCompensationRequested(event);
    }

    public void handleRestaurantCompensated(String orderId) {
        var order = findOrderById(OrderId.of(orderId));
        var compensatedOrder = order.sagaFailed();
        var savedOrder = orderRepository.save(compensatedOrder);
        
        cacheService.cacheOrder(savedOrder);
        
        var event = new OrderSagaFailedEvent(
            savedOrder.id().toString(),
            "Order saga failed and compensated",
            Instant.now()
        );
        
        eventPublisher.publishOrderSagaFailed(event);
    }

    public void handleDeliveryCompensated(String orderId) {
        var order = findOrderById(OrderId.of(orderId));
        
        var compensatingOrder = order.compensateRestaurant();
        var savedOrder = orderRepository.save(compensatingOrder);
        
        cacheService.cacheOrder(savedOrder);
        
        var event = new RestaurantCompensationRequestedEvent(
            savedOrder.id().toString(),
            savedOrder.restaurantId().toString(),
            "Delivery compensation completed",
            Instant.now()
        );
        
        eventPublisher.publishRestaurantCompensationRequested(event);
    }
    
    private Order findOrderById(OrderId orderId) {
        return orderRepository.findById(orderId)
            .orElseThrow(() -> new OrderNotFoundException("Order not found: " + orderId));
    }
}

