package com.delivery.bff.service;

import com.delivery.bff.client.RestaurantServiceClient;
import com.delivery.bff.dto.RestaurantResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantAggregationService {
    
    private final RestaurantServiceClient restaurantServiceClient;
    
    public RestaurantAggregationService(RestaurantServiceClient restaurantServiceClient) {
        this.restaurantServiceClient = restaurantServiceClient;
    }

    public List<RestaurantResponse> getAvailableRestaurants(String location, String cuisine) {
        return restaurantServiceClient.findAvailableRestaurants(location, cuisine);
    }

    public RestaurantResponse getRestaurantDetails(String restaurantId) {
        return restaurantServiceClient.findById(restaurantId);
    }

    public List<RestaurantResponse> getNearbyRestaurants(double latitude, double longitude, double radiusKm) {
        return restaurantServiceClient.findNearbyRestaurants(latitude, longitude, radiusKm);
    }
}

