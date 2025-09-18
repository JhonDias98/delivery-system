package com.delivery.restaurants.application.usecase;

import com.delivery.restaurants.application.dto.RestaurantResponse;
import com.delivery.restaurants.domain.exception.RestaurantNotFoundException;
import com.delivery.restaurants.domain.model.RestaurantId;
import com.delivery.restaurants.domain.port.RestaurantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class GetRestaurantUseCase {
    
    private final RestaurantRepository restaurantRepository;
    
    public GetRestaurantUseCase(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }
    
    public RestaurantResponse execute(String restaurantId) {
        var restaurant = restaurantRepository.findById(RestaurantId.of(restaurantId))
            .orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found: " + restaurantId));
        
        return RestaurantResponse.from(restaurant);
    }
}

