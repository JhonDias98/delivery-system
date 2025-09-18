package com.delivery.restaurants.domain.port;

import com.delivery.restaurants.domain.model.Restaurant;
import com.delivery.restaurants.domain.model.RestaurantId;
import com.delivery.restaurants.domain.model.RestaurantStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface RestaurantRepository {
    
    Restaurant save(Restaurant restaurant);
    
    Optional<Restaurant> findById(RestaurantId id);
    
    List<Restaurant> findAll();
    
    List<Restaurant> findByStatus(RestaurantStatus status);
    
    List<Restaurant> findActiveRestaurants();
    
    List<Restaurant> findByLocationRadius(BigDecimal latitude, BigDecimal longitude, double radiusKm);
    
    List<Restaurant> findByNameContaining(String name);
    
    List<Restaurant> findByCategory(String category);
    
    boolean existsById(RestaurantId id);
    
    void deleteById(RestaurantId id);
    
    long count();
    
    long countByStatus(RestaurantStatus status);
}

