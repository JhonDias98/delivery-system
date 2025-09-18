package com.delivery.orders.infrastructure.adapter.out.persistence;

import com.delivery.orders.domain.model.*;
import com.delivery.orders.domain.port.OrderRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class OrderRepositoryAdapter implements OrderRepository {
    
    private final Map<OrderId, Order> orders = new ConcurrentHashMap<>();
    
    @Override
    public Order save(Order order) {
        orders.put(order.id(), order);
        return order;
    }
    
    @Override
    public Optional<Order> findById(OrderId id) {
        return Optional.ofNullable(orders.get(id));
    }
    
    @Override
    public List<Order> findAll() {
        return List.copyOf(orders.values());
    }
    
    @Override
    public List<Order> findByCustomerId(CustomerId customerId) {
        return orders.values().stream()
            .filter(order -> order.customerId().equals(customerId))
            .toList();
    }
    
    @Override
    public List<Order> findByRestaurantId(RestaurantId restaurantId) {
        return orders.values().stream()
            .filter(order -> order.restaurantId().equals(restaurantId))
            .toList();
    }
    
    @Override
    public List<Order> findByStatus(OrderStatus status) {
        return orders.values().stream()
            .filter(order -> order.status().equals(status))
            .toList();
    }
    
    @Override
    public List<Order> findBySagaState(SagaState sagaState) {
        return orders.values().stream()
            .filter(order -> order.sagaState().equals(sagaState))
            .toList();
    }
    
    @Override
    public List<Order> findByCustomerIdAndStatus(CustomerId customerId, OrderStatus status) {
        return orders.values().stream()
            .filter(order -> order.customerId().equals(customerId) && order.status().equals(status))
            .toList();
    }
    
    @Override
    public List<Order> findByRestaurantIdAndStatus(RestaurantId restaurantId, OrderStatus status) {
        return orders.values().stream()
            .filter(order -> order.restaurantId().equals(restaurantId) && order.status().equals(status))
            .toList();
    }
    
    @Override
    public List<Order> findByCreatedAtBetween(Instant startDate, Instant endDate) {
        return orders.values().stream()
            .filter(order -> order.createdAt().isAfter(startDate) && order.createdAt().isBefore(endDate))
            .toList();
    }
    
    @Override
    public List<Order> findActiveOrdersByCustomer(CustomerId customerId) {
        return orders.values().stream()
            .filter(order -> order.customerId().equals(customerId))
            .filter(order -> order.status() != OrderStatus.DELIVERED && order.status() != OrderStatus.CANCELLED)
            .toList();
    }
    
    @Override
    public List<Order> findPendingSagaOrders() {
        return orders.values().stream()
            .filter(order -> !order.isCompleted() && !order.isFailed())
            .toList();
    }
    
    @Override
    public boolean existsById(OrderId id) {
        return orders.containsKey(id);
    }
    
    @Override
    public void deleteById(OrderId id) {
        orders.remove(id);
    }
    
    @Override
    public long count() {
        return orders.size();
    }
    
    @Override
    public long countByStatus(OrderStatus status) {
        return findByStatus(status).size();
    }
    
    @Override
    public long countByCustomerId(CustomerId customerId) {
        return findByCustomerId(customerId).size();
    }
    
    @Override
    public long countByRestaurantId(RestaurantId restaurantId) {
        return findByRestaurantId(restaurantId).size();
    }
    
    @Override
    public List<Order> findByCustomerIdOrderByCreatedAtDesc(CustomerId customerId) {
        return findByCustomerId(customerId).stream()
            .sorted((o1, o2) -> o2.createdAt().compareTo(o1.createdAt()))
            .toList();
    }
}

