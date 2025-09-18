package com.delivery.restaurants.infrastructure.adapter.in.web;

import com.delivery.restaurants.application.dto.RestaurantResponse;
import com.delivery.restaurants.application.usecase.CreateRestaurantUseCase;
import com.delivery.restaurants.application.usecase.GetRestaurantUseCase;
import com.delivery.restaurants.infrastructure.adapter.in.web.dto.CreateRestaurantRequest;
import com.delivery.restaurants.infrastructure.adapter.in.web.mapper.RestaurantWebMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/restaurants")
@CrossOrigin(origins = "*")
public class RestaurantController {
    
    private final CreateRestaurantUseCase createRestaurantUseCase;
    private final GetRestaurantUseCase getRestaurantUseCase;
    private final RestaurantWebMapper restaurantWebMapper;
    
    public RestaurantController(
        CreateRestaurantUseCase createRestaurantUseCase,
        GetRestaurantUseCase getRestaurantUseCase,
        RestaurantWebMapper restaurantWebMapper
    ) {
        this.createRestaurantUseCase = createRestaurantUseCase;
        this.getRestaurantUseCase = getRestaurantUseCase;
        this.restaurantWebMapper = restaurantWebMapper;
    }
    
    @PostMapping
    public ResponseEntity<RestaurantResponse> createRestaurant(@Valid @RequestBody CreateRestaurantRequest request) {
        var command = restaurantWebMapper.toCommand(request);
        var response = createRestaurantUseCase.execute(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping("/{restaurantId}")
    public ResponseEntity<RestaurantResponse> getRestaurant(@PathVariable String restaurantId) {
        var response = getRestaurantUseCase.execute(restaurantId);
        return ResponseEntity.ok(response);
    }
}

