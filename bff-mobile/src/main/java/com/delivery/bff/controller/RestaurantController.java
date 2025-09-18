package com.delivery.bff.controller;

import com.delivery.bff.dto.RestaurantResponse;
import com.delivery.bff.service.RestaurantAggregationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mobile/restaurants")
@CrossOrigin(origins = "*")
public class RestaurantController {
    
    private final RestaurantAggregationService restaurantAggregationService;
    
    public RestaurantController(RestaurantAggregationService restaurantAggregationService) {
        this.restaurantAggregationService = restaurantAggregationService;
    }

    @GetMapping
    public ResponseEntity<List<RestaurantResponse>> getAvailableRestaurants(
        @RequestParam(required = false) String location,
        @RequestParam(required = false) String cuisine
    ) {
        var restaurants = restaurantAggregationService.getAvailableRestaurants(location, cuisine);
        return ResponseEntity.ok(restaurants);
    }

    @GetMapping("/{restaurantId}")
    public ResponseEntity<RestaurantResponse> getRestaurantDetails(@PathVariable String restaurantId) {
        var restaurant = restaurantAggregationService.getRestaurantDetails(restaurantId);
        return ResponseEntity.ok(restaurant);
    }

    @GetMapping("/nearby")
    public ResponseEntity<List<RestaurantResponse>> getNearbyRestaurants(
        @RequestParam double latitude,
        @RequestParam double longitude,
        @RequestParam(defaultValue = "5.0") double radiusKm
    ) {
        var restaurants = restaurantAggregationService.getNearbyRestaurants(latitude, longitude, radiusKm);
        return ResponseEntity.ok(restaurants);
    }
}

