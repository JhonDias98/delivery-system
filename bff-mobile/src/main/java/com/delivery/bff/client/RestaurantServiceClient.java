package com.delivery.bff.client;

import com.delivery.bff.dto.RestaurantResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class RestaurantServiceClient {
    
    private final RestTemplate restTemplate;
    private final String restaurantsServiceUrl;
    
    public RestaurantServiceClient(
        RestTemplate restTemplate,
        @Value("${services.restaurants.url:http://localhost:8080}") String restaurantsServiceUrl
    ) {
        this.restTemplate = restTemplate;
        this.restaurantsServiceUrl = restaurantsServiceUrl;
    }
    
    @SuppressWarnings("unchecked")
    public List<RestaurantResponse> findAvailableRestaurants(String location, String cuisine) {
        var url = restaurantsServiceUrl + "/api/restaurants";
        if (location != null || cuisine != null) {
            url += "?";
            if (location != null) url += "location=" + location;
            if (cuisine != null) url += (location != null ? "&" : "") + "cuisine=" + cuisine;
        }
        
        return restTemplate.getForObject(url, List.class);
    }
    
    public RestaurantResponse findById(String restaurantId) {
        return restTemplate.getForObject(
            restaurantsServiceUrl + "/api/restaurants/" + restaurantId,
            RestaurantResponse.class
        );
    }
    
    @SuppressWarnings("unchecked")
    public List<RestaurantResponse> findNearbyRestaurants(double latitude, double longitude, double radiusKm) {
        var url = String.format(
            "%s/api/restaurants/nearby?latitude=%f&longitude=%f&radius=%f",
            restaurantsServiceUrl, latitude, longitude, radiusKm
        );
        
        return restTemplate.getForObject(url, List.class);
    }
}

