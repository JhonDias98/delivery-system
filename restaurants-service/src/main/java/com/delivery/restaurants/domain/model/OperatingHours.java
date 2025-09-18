package com.delivery.restaurants.domain.model;

import java.time.LocalTime;
import java.util.List;

public record OperatingHours(
    LocalTime openTime,
    LocalTime closeTime,
    List<DayOfWeek> operatingDays
) {
    public OperatingHours {
        if (openTime == null) {
            throw new IllegalArgumentException("Open time cannot be null");
        }
        if (closeTime == null) {
            throw new IllegalArgumentException("Close time cannot be null");
        }
        if (operatingDays == null || operatingDays.isEmpty()) {
            throw new IllegalArgumentException("Operating days cannot be null or empty");
        }
    }
    
    public boolean isOpenNow() {
        var now = LocalTime.now();
        var today = java.time.LocalDate.now().getDayOfWeek();
        
        var isOperatingDay = operatingDays.contains(DayOfWeek.valueOf(today.name()));
        
        if (!isOperatingDay) {
            return false;
        }
        
        if (closeTime.isBefore(openTime)) {
            return now.isAfter(openTime) || now.isBefore(closeTime);
        }
        
        return now.isAfter(openTime) && now.isBefore(closeTime);
    }
}

