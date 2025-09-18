package com.delivery.orders.infrastructure.config;

import com.delivery.orders.domain.port.CacheService;
import com.delivery.orders.domain.port.OrderRepository;
import com.delivery.orders.domain.port.SagaEventPublisher;
import com.delivery.orders.domain.service.OrderSagaOrchestrator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {
    
    @Bean
    public OrderSagaOrchestrator orderSagaOrchestrator(
        OrderRepository orderRepository,
        SagaEventPublisher sagaEventPublisher,
        CacheService cacheService
    ) {
        return new OrderSagaOrchestrator(orderRepository, sagaEventPublisher, cacheService);
    }
    
    @Bean
    public SagaEventPublisher sagaEventPublisher() {
        return new SagaEventPublisher() {
            @Override
            public void publishRestaurantValidationRequested(com.delivery.orders.domain.event.saga.RestaurantValidationRequestedEvent event) {
                System.out.println("Publishing: " + event);
            }
            
            @Override
            public void publishDeliveryAllocationRequested(com.delivery.orders.domain.event.saga.DeliveryAllocationRequestedEvent event) {
                System.out.println("Publishing: " + event);
            }
            
            @Override
            public void publishPaymentProcessingRequested(com.delivery.orders.domain.event.saga.PaymentProcessingRequestedEvent event) {
                System.out.println("Publishing: " + event);
            }
            
            @Override
            public void publishRestaurantCompensationRequested(com.delivery.orders.domain.event.saga.RestaurantCompensationRequestedEvent event) {
                System.out.println("Publishing: " + event);
            }
            
            @Override
            public void publishDeliveryCompensationRequested(com.delivery.orders.domain.event.saga.DeliveryCompensationRequestedEvent event) {
                System.out.println("Publishing: " + event);
            }
            
            @Override
            public void publishPaymentCompensationRequested(com.delivery.orders.domain.event.saga.PaymentCompensationRequestedEvent event) {
                System.out.println("Publishing: " + event);
            }
            
            @Override
            public void publishOrderConfirmed(com.delivery.orders.domain.event.order.OrderConfirmedEvent event) {
                System.out.println("Publishing: " + event);
            }
            
            @Override
            public void publishOrderSagaFailed(com.delivery.orders.domain.event.order.OrderSagaFailedEvent event) {
                System.out.println("Publishing: " + event);
            }
        };
    }
    
    @Bean
    public CacheService cacheService() {
        return new CacheService() {
            @Override
            public void cacheOrder(com.delivery.orders.domain.model.Order order) {
                System.out.println("Caching order: " + order.id());
            }
            
            @Override
            public java.util.Optional<com.delivery.orders.domain.model.Order> getCachedOrder(com.delivery.orders.domain.model.OrderId orderId) {
                return java.util.Optional.empty();
            }
            
            @Override
            public void evictOrder(com.delivery.orders.domain.model.OrderId orderId) {
                System.out.println("Evicting order: " + orderId);
            }
            
            @Override
            public void cacheOrderList(String cacheKey, java.util.List<com.delivery.orders.domain.model.Order> orders, java.time.Duration ttl) {
                System.out.println("Caching order list: " + cacheKey);
            }
            
            @Override
            public java.util.Optional<java.util.List<com.delivery.orders.domain.model.Order>> getCachedOrderList(String cacheKey) {
                return java.util.Optional.empty();
            }
            
            @Override
            public void evictOrderList(String cacheKey) {
                System.out.println("Evicting order list: " + cacheKey);
            }
            
            @Override
            public void invalidateCustomerOrderCaches(com.delivery.orders.domain.model.CustomerId customerId) {
                System.out.println("Invalidating customer caches: " + customerId);
            }
            
            @Override
            public void invalidateRestaurantOrderCaches(com.delivery.orders.domain.model.RestaurantId restaurantId) {
                System.out.println("Invalidating restaurant caches: " + restaurantId);
            }
        };
    }
}

