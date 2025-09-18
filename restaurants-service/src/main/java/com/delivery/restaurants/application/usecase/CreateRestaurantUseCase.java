package com.delivery.restaurants.application.usecase;

import com.delivery.restaurants.application.dto.CreateRestaurantCommand;
import com.delivery.restaurants.application.dto.RestaurantResponse;
import com.delivery.restaurants.domain.service.RestaurantService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CreateRestaurantUseCase {
    
    private final RestaurantService restaurantService;
    
    public CreateRestaurantUseCase(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }
    
    public RestaurantResponse execute(CreateRestaurantCommand command) {
        var restaurant = restaurantService.createRestaurant(command);
        return RestaurantResponse.from(restaurant);
    }
}

