package com.delivery.restaurants.infrastructure.config;

import com.delivery.restaurants.domain.port.CacheService;
import com.delivery.restaurants.domain.port.EventPublisher;
import com.delivery.restaurants.domain.port.RestaurantRepository;
import com.delivery.restaurants.domain.service.RestaurantService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {
    
    @Bean
    public RestaurantService restaurantService(
        RestaurantRepository restaurantRepository,
        EventPublisher eventPublisher,
        CacheService cacheService
    ) {
        return new RestaurantService(restaurantRepository, eventPublisher, cacheService);
    }
    
    @Bean
    public EventPublisher eventPublisher() {
        return new EventPublisher() {
            @Override
            public void publishRestaurantCreated(com.delivery.restaurants.domain.event.RestaurantCreatedEvent event) {
                System.out.println("Publishing: " + event);
            }
            
            @Override
            public void publishRestaurantActivated(com.delivery.restaurants.domain.event.RestaurantActivatedEvent event) {
                System.out.println("Publishing: " + event);
            }
            
            @Override
            public void publishRestaurantDeactivated(com.delivery.restaurants.domain.event.RestaurantDeactivatedEvent event) {
                System.out.println("Publishing: " + event);
            }
            
            @Override
            public void publishMenuUpdated(com.delivery.restaurants.domain.event.MenuUpdatedEvent event) {
                System.out.println("Publishing: " + event);
            }
            
            @Override
            public void publishRestaurantRatingUpdated(com.delivery.restaurants.domain.event.RestaurantRatingUpdatedEvent event) {
                System.out.println("Publishing: " + event);
            }
        };
    }
    
    @Bean
    public CacheService cacheService() {
        return new CacheService() {
            @Override
            public void cacheRestaurant(com.delivery.restaurants.domain.model.Restaurant restaurant) {
                System.out.println("Caching restaurant: " + restaurant.id());
            }
            
            @Override
            public java.util.Optional<com.delivery.restaurants.domain.model.Restaurant> getCachedRestaurant(com.delivery.restaurants.domain.model.RestaurantId restaurantId) {
                return java.util.Optional.empty();
            }
            
            @Override
            public void evictRestaurant(com.delivery.restaurants.domain.model.RestaurantId restaurantId) {
                System.out.println("Evicting restaurant: " + restaurantId);
            }
            
            @Override
            public void cacheRestaurantList(String cacheKey, java.util.List<com.delivery.restaurants.domain.model.Restaurant> restaurants, java.time.Duration ttl) {
                System.out.println("Caching restaurant list: " + cacheKey);
            }
            
            @Override
            public java.util.Optional<java.util.List<com.delivery.restaurants.domain.model.Restaurant>> getCachedRestaurantList(String cacheKey) {
                return java.util.Optional.empty();
            }
            
            @Override
            public void evictRestaurantList(String cacheKey) {
                System.out.println("Evicting restaurant list: " + cacheKey);
            }
            
            @Override
            public void cacheMenuItem(com.delivery.restaurants.domain.model.RestaurantId restaurantId, com.delivery.restaurants.domain.model.MenuItem menuItem) {
                System.out.println("Caching menu item: " + menuItem.id());
            }
            
            @Override
            public java.util.Optional<com.delivery.restaurants.domain.model.MenuItem> getCachedMenuItem(com.delivery.restaurants.domain.model.RestaurantId restaurantId, com.delivery.restaurants.domain.model.MenuItemId menuItemId) {
                return java.util.Optional.empty();
            }
            
            @Override
            public void evictMenuItem(com.delivery.restaurants.domain.model.RestaurantId restaurantId, com.delivery.restaurants.domain.model.MenuItemId menuItemId) {
                System.out.println("Evicting menu item: " + menuItemId);
            }
            
            @Override
            public void cacheRestaurantMenu(com.delivery.restaurants.domain.model.RestaurantId restaurantId, java.util.List<com.delivery.restaurants.domain.model.MenuItem> menu) {
                System.out.println("Caching restaurant menu: " + restaurantId);
            }
            
            @Override
            public java.util.Optional<java.util.List<com.delivery.restaurants.domain.model.MenuItem>> getCachedRestaurantMenu(com.delivery.restaurants.domain.model.RestaurantId restaurantId) {
                return java.util.Optional.empty();
            }
            
            @Override
            public void evictRestaurantMenu(com.delivery.restaurants.domain.model.RestaurantId restaurantId) {
                System.out.println("Evicting restaurant menu: " + restaurantId);
            }
            
            @Override
            public void invalidateAll() {
                System.out.println("Invalidating all caches");
            }
            
            @Override
            public void invalidateRestaurantCaches(com.delivery.restaurants.domain.model.RestaurantId restaurantId) {
                System.out.println("Invalidating restaurant caches: " + restaurantId);
            }
            
            @Override
            public void invalidateMenuCaches(com.delivery.restaurants.domain.model.RestaurantId restaurantId) {
                System.out.println("Invalidating menu caches: " + restaurantId);
            }
            
            @Override
            public com.delivery.restaurants.domain.valueobject.CacheStats getL1CacheStats() {
                return new com.delivery.restaurants.domain.valueobject.CacheStats(0L, 0L, 0L, 0.0);
            }
            
            @Override
            public com.delivery.restaurants.domain.valueobject.CacheStats getL2CacheStats() {
                return new com.delivery.restaurants.domain.valueobject.CacheStats(0L, 0L, 0L, 0.0);
            }
            
            @Override
            public com.delivery.restaurants.domain.valueobject.CacheStats getStats() {
                return new com.delivery.restaurants.domain.valueobject.CacheStats(0L, 0L, 0L, 0.0);
            }
        };
    }
}

