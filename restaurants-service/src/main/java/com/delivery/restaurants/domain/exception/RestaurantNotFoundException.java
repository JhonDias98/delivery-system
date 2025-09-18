package com.delivery.restaurants.domain.exception;

public class RestaurantNotFoundException extends RuntimeException {
    public RestaurantNotFoundException(String message) {
        super(message);
    }
    
    public RestaurantNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

