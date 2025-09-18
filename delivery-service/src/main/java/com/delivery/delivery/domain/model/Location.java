package com.delivery.delivery.domain.model;

import java.math.BigDecimal;
import java.time.Instant;

public record Location(
    BigDecimal latitude,
    BigDecimal longitude,
    String address,
    Instant timestamp
) {
    public Location {
        if (latitude == null) {
            throw new IllegalArgumentException("Latitude cannot be null");
        }
        if (longitude == null) {
            throw new IllegalArgumentException("Longitude cannot be null");
        }
        if (latitude.compareTo(BigDecimal.valueOf(-90)) < 0 || latitude.compareTo(BigDecimal.valueOf(90)) > 0) {
            throw new IllegalArgumentException("Latitude must be between -90 and 90");
        }
        if (longitude.compareTo(BigDecimal.valueOf(-180)) < 0 || longitude.compareTo(BigDecimal.valueOf(180)) > 0) {
            throw new IllegalArgumentException("Longitude must be between -180 and 180");
        }
        if (timestamp == null) {
            timestamp = Instant.now();
        }
    }
    
    /**
     * Calculate distance to another location using Haversine formula
     * @param other the other location
     * @return distance in kilometers
     */
    public double distanceTo(Location other) {
        final double R = 6371;
        
        double lat1Rad = Math.toRadians(this.latitude.doubleValue());
        double lat2Rad = Math.toRadians(other.latitude.doubleValue());
        double deltaLatRad = Math.toRadians(other.latitude.subtract(this.latitude).doubleValue());
        double deltaLonRad = Math.toRadians(other.longitude.subtract(this.longitude).doubleValue());
        
        double a = Math.sin(deltaLatRad / 2) * Math.sin(deltaLatRad / 2) +
                   Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                   Math.sin(deltaLonRad / 2) * Math.sin(deltaLonRad / 2);
        
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        
        return R * c;
    }
    
    public boolean isWithinRadius(Location center, double radiusKm) {
        return distanceTo(center) <= radiusKm;
    }
}

