package com.delivery.restaurants.domain.port;

import com.delivery.restaurants.domain.model.*;
import com.delivery.restaurants.domain.valueobject.CacheStats;

import java.time.Duration;
import java.util.List;
import java.util.Optional;


public interface CacheService {

    void cacheRestaurant(Restaurant restaurant);
    
    Optional<Restaurant> getCachedRestaurant(RestaurantId restaurantId);
    
    void evictRestaurant(RestaurantId restaurantId);

    void cacheRestaurantList(String cacheKey, List<Restaurant> restaurants, Duration ttl);
    
    Optional<List<Restaurant>> getCachedRestaurantList(String cacheKey);
    
    void evictRestaurantList(String cacheKey);

    void cacheMenuItem(RestaurantId restaurantId, MenuItem menuItem);
    
    Optional<MenuItem> getCachedMenuItem(RestaurantId restaurantId, MenuItemId menuItemId);
    
    void evictMenuItem(RestaurantId restaurantId, MenuItemId menuItemId);

    void cacheRestaurantMenu(RestaurantId restaurantId, List<MenuItem> menu);
    
    Optional<List<MenuItem>> getCachedRestaurantMenu(RestaurantId restaurantId);
    
    void evictRestaurantMenu(RestaurantId restaurantId);

    void invalidateAll();
    
    void invalidateRestaurantCaches(RestaurantId restaurantId);
    
    void invalidateMenuCaches(RestaurantId restaurantId);

    CacheStats getL1CacheStats();
    
    CacheStats getL2CacheStats();
    
    CacheStats getStats();
}

