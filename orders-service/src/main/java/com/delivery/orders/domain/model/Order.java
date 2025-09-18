package com.delivery.orders.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record Order(
    OrderId id,
    CustomerId customerId,
    RestaurantId restaurantId,
    DeliveryAddress deliveryAddress,
    List<OrderItem> items,
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
    
    public Order {
        if (id == null) {
            throw new IllegalArgumentException("Order ID cannot be null");
        }
        if (customerId == null) {
            throw new IllegalArgumentException("Customer ID cannot be null");
        }
        if (restaurantId == null) {
            throw new IllegalArgumentException("Restaurant ID cannot be null");
        }
        if (deliveryAddress == null) {
            throw new IllegalArgumentException("Delivery address cannot be null");
        }
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Order items cannot be null or empty");
        }
        if (status == null) {
            throw new IllegalArgumentException("Order status cannot be null");
        }
        if (subtotal == null || subtotal.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Subtotal must be positive");
        }
        if (deliveryFee == null || deliveryFee.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Delivery fee cannot be negative");
        }
        if (totalAmount == null || totalAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Total amount must be positive");
        }
        if (paymentMethod == null) {
            throw new IllegalArgumentException("Payment method cannot be null");
        }
        if (createdAt == null) {
            createdAt = Instant.now();
        }
        if (updatedAt == null) {
            updatedAt = createdAt;
        }
        if (sagaState == null) {
            sagaState = SagaState.STARTED;
        }
    }
    
    public static Order create(
        CustomerId customerId,
        RestaurantId restaurantId,
        DeliveryAddress deliveryAddress,
        List<OrderItem> items,
        BigDecimal deliveryFee,
        PaymentMethod paymentMethod,
        String specialInstructions
    ) {
        var subtotal = items.stream()
            .map(item -> item.price().multiply(BigDecimal.valueOf(item.quantity())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        var totalAmount = subtotal.add(deliveryFee);
        
        return new Order(
            OrderId.generate(),
            customerId,
            restaurantId,
            deliveryAddress,
            List.copyOf(items),
            OrderStatus.PENDING,
            subtotal,
            deliveryFee,
            totalAmount,
            paymentMethod,
            specialInstructions,
            Instant.now(),
            Instant.now(),
            SagaState.STARTED
        );
    }
    
    public Order startSaga() {
        if (sagaState != SagaState.STARTED) {
            throw new IllegalStateException("Saga can only be started from STARTED state");
        }
        return updateSagaState(SagaState.RESTAURANT_VALIDATION_PENDING);
    }
    
    public Order restaurantValidated() {
        if (sagaState != SagaState.RESTAURANT_VALIDATION_PENDING) {
            throw new IllegalStateException("Invalid saga state for restaurant validation");
        }
        return updateSagaState(SagaState.DELIVERY_ALLOCATION_PENDING);
    }
    
    public Order deliveryAllocated() {
        if (sagaState != SagaState.DELIVERY_ALLOCATION_PENDING) {
            throw new IllegalStateException("Invalid saga state for delivery allocation");
        }
        return updateSagaState(SagaState.PAYMENT_PROCESSING_PENDING);
    }
    
    public Order paymentProcessed() {
        if (sagaState != SagaState.PAYMENT_PROCESSING_PENDING) {
            throw new IllegalStateException("Invalid saga state for payment processing");
        }
        return updateStatus(OrderStatus.CONFIRMED).updateSagaState(SagaState.COMPLETED);
    }
    
    public Order compensateRestaurant() {
        return updateSagaState(SagaState.RESTAURANT_COMPENSATION_PENDING);
    }
    
    public Order compensateDelivery() {
        return updateSagaState(SagaState.DELIVERY_COMPENSATION_PENDING);
    }
    
    public Order compensatePayment() {
        return updateSagaState(SagaState.PAYMENT_COMPENSATION_PENDING);
    }
    
    public Order sagaFailed() {
        return updateStatus(OrderStatus.CANCELLED).updateSagaState(SagaState.FAILED);
    }
    
    public Order updateStatus(OrderStatus newStatus) {
        return new Order(
            id, customerId, restaurantId, deliveryAddress, items,
            newStatus, subtotal, deliveryFee, totalAmount, paymentMethod,
            specialInstructions, createdAt, Instant.now(), sagaState
        );
    }
    
    private Order updateSagaState(SagaState newSagaState) {
        return new Order(
            id, customerId, restaurantId, deliveryAddress, items,
            status, subtotal, deliveryFee, totalAmount, paymentMethod,
            specialInstructions, createdAt, Instant.now(), newSagaState
        );
    }
    
    public boolean isCompleted() {
        return sagaState == SagaState.COMPLETED;
    }
    
    public boolean isFailed() {
        return sagaState == SagaState.FAILED;
    }
    
    public boolean requiresCompensation() {
        return sagaState.name().contains("COMPENSATION");
    }
}

