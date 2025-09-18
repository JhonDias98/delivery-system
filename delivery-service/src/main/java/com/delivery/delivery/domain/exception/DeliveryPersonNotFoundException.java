package com.delivery.delivery.domain.exception;

public class DeliveryPersonNotFoundException extends RuntimeException {
    
    public DeliveryPersonNotFoundException(String message) {
        super(message);
    }
    
    public DeliveryPersonNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

