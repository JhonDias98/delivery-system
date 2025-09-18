package com.delivery.orders.domain.port;

import com.delivery.orders.domain.model.*;
import java.util.List;
import java.util.Optional;

public interface CacheService {
    
    void cacheOrder(Order order);
    
    Optional<Order> getCachedOrder(OrderId orderId);
    
    void evictOrder(OrderId orderId);
    
    void cacheOrderList(String cacheKey, List<Order> orders, java.time.Duration ttl);
    
    Optional<List<Order>> getCachedOrderList(String cacheKey);
    
    void evictOrderList(String cacheKey);
    
    void invalidateCustomerOrderCaches(CustomerId customerId);
    
    void invalidateRestaurantOrderCaches(RestaurantId restaurantId);
}

