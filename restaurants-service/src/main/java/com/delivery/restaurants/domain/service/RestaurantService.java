package com.delivery.restaurants.domain.service;

import com.delivery.restaurants.application.dto.CreateRestaurantCommand;
import com.delivery.restaurants.domain.model.*;
import com.delivery.restaurants.domain.port.*;
import com.delivery.restaurants.domain.exception.RestaurantNotFoundException;
import com.delivery.restaurants.domain.event.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;
import java.util.Optional;

public class RestaurantService {
    
    private final RestaurantRepository repository;
    private final EventPublisher eventPublisher;
    private final CacheService cacheService;
    
    private static final Duration RESTAURANT_CACHE_TTL = Duration.ofMinutes(30);
    private static final Duration RESTAURANT_LIST_CACHE_TTL = Duration.ofMinutes(10);
    private static final Duration MENU_CACHE_TTL = Duration.ofHours(1);
    
    public RestaurantService(
        RestaurantRepository repository,
        EventPublisher eventPublisher,
        CacheService cacheService
    ) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
        this.cacheService = cacheService;
    }

    public Restaurant createRestaurant(CreateRestaurantCommand command) {
        var operatingHours = command.operatingHours().isEmpty() ?
            null : command.operatingHours().get(0);
            
        var restaurant = Restaurant.create(
            command.name(),
            command.description(),
            command.address(),
            command.contactInfo(),
            operatingHours
        );
        
        var savedRestaurant = repository.save(restaurant);
        
        cacheService.cacheRestaurant(savedRestaurant);
        
        var event = new RestaurantCreatedEvent(
            savedRestaurant.id().toString(),
            savedRestaurant.name(),
            savedRestaurant.address().getFullAddress(),
            java.time.Instant.now()
        );
        eventPublisher.publishRestaurantCreated(event);
        
        return savedRestaurant;
    }

    public Optional<Restaurant> findById(RestaurantId id) {
        var cached = cacheService.getCachedRestaurant(id);
        if (cached.isPresent()) {
            return cached;
        }
        
        var restaurant = repository.findById(id);
        restaurant.ifPresent(cacheService::cacheRestaurant);
        
        return restaurant;
    }

    public Restaurant activateRestaurant(RestaurantId id) {
        var restaurant = repository.findById(id)
            .orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found: " + id));
        
        var activatedRestaurant = restaurant.activate();
        var savedRestaurant = repository.save(activatedRestaurant);
        
        cacheService.cacheRestaurant(savedRestaurant);
        
        var event = new RestaurantActivatedEvent(
            savedRestaurant.id().toString(),
            savedRestaurant.name(),
            java.time.Instant.now()
        );
        eventPublisher.publishRestaurantActivated(event);
        
        return savedRestaurant;
    }

    public Restaurant deactivateRestaurant(RestaurantId id, String reason) {
        var restaurant = repository.findById(id)
            .orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found: " + id));
        
        var deactivatedRestaurant = restaurant.deactivate();
        var savedRestaurant = repository.save(deactivatedRestaurant);

        cacheService.cacheRestaurant(savedRestaurant);
        
        var event = new RestaurantDeactivatedEvent(
            savedRestaurant.id().toString(),
            savedRestaurant.name(),
            reason,
            java.time.Instant.now()
        );
        eventPublisher.publishRestaurantDeactivated(event);
        
        return savedRestaurant;
    }

    public Restaurant updateMenu(RestaurantId id, List<MenuItem> newMenu) {
        var restaurant = repository.findById(id)
            .orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found: " + id));
        
        var updatedRestaurant = restaurant.updateMenu(newMenu);
        var savedRestaurant = repository.save(updatedRestaurant);
        
        cacheService.invalidateMenuCaches(id);
        cacheService.cacheRestaurant(savedRestaurant);
        
        var event = new MenuUpdatedEvent(
            savedRestaurant.id().toString(),
            savedRestaurant.name(),
            newMenu.stream().map(item -> item.id().toString()).toList(),
            java.time.Instant.now()
        );
        eventPublisher.publishMenuUpdated(event);
        
        return savedRestaurant;
    }

    public List<Restaurant> findActiveRestaurants() {
        var cacheKey = "active_restaurants";

        var cached = cacheService.getCachedRestaurantList(cacheKey);
        if (cached.isPresent()) {
            return cached.get();
        }

        var restaurants = repository.findActiveRestaurants();
        cacheService.cacheRestaurantList(cacheKey, restaurants, RESTAURANT_LIST_CACHE_TTL);
        
        return restaurants;
    }

    public List<Restaurant> findByLocation(BigDecimal latitude, BigDecimal longitude, double radiusKm) {
        var cacheKey = String.format("restaurants_location_%s_%s_%s", 
            latitude.toString(), longitude.toString(), radiusKm);
        
        var cached = cacheService.getCachedRestaurantList(cacheKey);
        if (cached.isPresent()) {
            return cached.get();
        }
        
        var restaurants = repository.findByLocationRadius(latitude, longitude, radiusKm);
        cacheService.cacheRestaurantList(cacheKey, restaurants, RESTAURANT_LIST_CACHE_TTL);
        
        return restaurants;
    }

    public List<Restaurant> searchByName(String name) {
        var cacheKey = "restaurants_search_" + name.toLowerCase();
        
        var cached = cacheService.getCachedRestaurantList(cacheKey);
        if (cached.isPresent()) {
            return cached.get();
        }
        
        var restaurants = repository.findByNameContaining(name);
        cacheService.cacheRestaurantList(cacheKey, restaurants, RESTAURANT_LIST_CACHE_TTL);
        
        return restaurants;
    }

    public Restaurant updateRating(RestaurantId id, BigDecimal newRating, int newTotalReviews) {
        var restaurant = repository.findById(id)
            .orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found: " + id));
        
        var updatedRestaurant = restaurant.updateRating(newRating);
        var savedRestaurant = repository.save(updatedRestaurant);
        
        cacheService.cacheRestaurant(savedRestaurant);

        var event = new RestaurantRatingUpdatedEvent(
            savedRestaurant.id().toString(),
            savedRestaurant.name(),
            newRating,
            newTotalReviews,
            java.time.Instant.now()
        );
        eventPublisher.publishRestaurantRatingUpdated(event);
        
        return savedRestaurant;
    }
}

