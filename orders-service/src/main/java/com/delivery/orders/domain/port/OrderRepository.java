package com.delivery.orders.domain.port;

import com.delivery.orders.domain.model.*;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    
    Order save(Order order);
    
    Optional<Order> findById(OrderId id);
    
    List<Order> findAll();
    
    List<Order> findByCustomerId(CustomerId customerId);
    
    List<Order> findByRestaurantId(RestaurantId restaurantId);
    
    List<Order> findByStatus(OrderStatus status);
    
    List<Order> findBySagaState(SagaState sagaState);
    
    List<Order> findByCustomerIdAndStatus(CustomerId customerId, OrderStatus status);
    
    List<Order> findByRestaurantIdAndStatus(RestaurantId restaurantId, OrderStatus status);
    
    List<Order> findByCreatedAtBetween(Instant startDate, Instant endDate);
    
    List<Order> findActiveOrdersByCustomer(CustomerId customerId);
    
    List<Order> findPendingSagaOrders();
    
    boolean existsById(OrderId id);
    
    void deleteById(OrderId id);
    
    long count();
    
    long countByStatus(OrderStatus status);
    
    long countByCustomerId(CustomerId customerId);
    
    long countByRestaurantId(RestaurantId restaurantId);
    
    List<Order> findByCustomerIdOrderByCreatedAtDesc(CustomerId customerId);
}

