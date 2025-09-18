package com.delivery.delivery.domain.model;

import java.time.Instant;

public record DeliveryEvent(
    DeliveryEventId id,
    DeliveryEventType type,
    String description,
    Location location,
    Instant timestamp
) {
    public DeliveryEvent {
        if (id == null) {
            throw new IllegalArgumentException("Event ID cannot be null");
        }
        if (type == null) {
            throw new IllegalArgumentException("Event type cannot be null");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description cannot be null or blank");
        }
        if (timestamp == null) {
            throw new IllegalArgumentException("Timestamp cannot be null");
        }
    }
}

