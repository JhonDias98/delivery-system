package com.delivery.delivery.domain.valueobject;

import com.delivery.delivery.domain.model.DeliveryPerson;

public record ScoredDeliveryPerson(
    DeliveryPerson deliveryPerson, 
    double score
) {
    
    public ScoredDeliveryPerson {
        if (deliveryPerson == null) {
            throw new IllegalArgumentException("DeliveryPerson cannot be null");
        }
        if (score < 0) {
            throw new IllegalArgumentException("Score cannot be negative");
        }
    }
}

