package com.delivery.delivery.application.dto;

import com.delivery.delivery.domain.model.DeliveryEvent;
import com.delivery.delivery.domain.model.DeliveryEventType;

import java.time.Instant;

public record DeliveryEventDto(
    String id,
    DeliveryEventType type,
    String description,
    Instant timestamp
) {
    public static DeliveryEventDto from(DeliveryEvent event) {
        return new DeliveryEventDto(
            event.id().toString(),
            event.type(),
            event.description(),
            event.timestamp()
        );
    }
}

