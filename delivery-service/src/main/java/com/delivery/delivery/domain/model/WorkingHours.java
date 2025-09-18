package com.delivery.delivery.domain.model;

import java.time.LocalTime;

public record WorkingHours(
    LocalTime startTime,
    LocalTime endTime
) {
    public WorkingHours {
        if (startTime == null) {
            throw new IllegalArgumentException("Start time cannot be null");
        }
        if (endTime == null) {
            throw new IllegalArgumentException("End time cannot be null");
        }
    }
    
    public boolean isWorkingNow() {
        var now = LocalTime.now();

        if (endTime.isBefore(startTime)) {
            return now.isAfter(startTime) || now.isBefore(endTime);
        }
        
        return now.isAfter(startTime) && now.isBefore(endTime);
    }
    
    public boolean isWithinWorkingHours(LocalTime time) {
        if (endTime.isBefore(startTime)) {
            return time.isAfter(startTime) || time.isBefore(endTime);
        }
        
        return time.isAfter(startTime) && time.isBefore(endTime);
    }
}

