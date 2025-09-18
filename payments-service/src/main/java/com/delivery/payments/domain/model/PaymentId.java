package com.delivery.payments.domain.model;

import java.util.UUID;

public record PaymentId(UUID value) {
    
    public PaymentId {
        if (value == null) {
            throw new IllegalArgumentException("Payment ID cannot be null");
        }
    }
    
    public static PaymentId generate() {
        return new PaymentId(UUID.randomUUID());
    }
    
    public static PaymentId of(String value) {
        return new PaymentId(UUID.fromString(value));
    }
    
    @Override
    public String toString() {
        return value.toString();
    }
}

