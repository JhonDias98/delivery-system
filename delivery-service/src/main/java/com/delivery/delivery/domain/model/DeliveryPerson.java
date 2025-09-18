package com.delivery.delivery.domain.model;

import java.math.BigDecimal;
import java.time.Instant;

public record DeliveryPerson(
    DeliveryPersonId id,
    String name,
    String email,
    String phone,
    String documentNumber,
    VehicleInfo vehicle,
    DeliveryPersonStatus status,
    Location currentLocation,
    WorkingHours workingHours,
    BigDecimal rating,
    int totalDeliveries,
    Instant createdAt,
    Instant updatedAt
) {
    
    public DeliveryPerson {
        if (id == null) {
            throw new IllegalArgumentException("Delivery person ID cannot be null");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or blank");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be null or blank");
        }
        if (phone == null || phone.isBlank()) {
            throw new IllegalArgumentException("Phone cannot be null or blank");
        }
        if (documentNumber == null || documentNumber.isBlank()) {
            throw new IllegalArgumentException("Document number cannot be null or blank");
        }
        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle info cannot be null");
        }
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }
        if (workingHours == null) {
            throw new IllegalArgumentException("Working hours cannot be null");
        }
        if (rating != null && (rating.compareTo(BigDecimal.ZERO) < 0 || rating.compareTo(BigDecimal.valueOf(5)) > 0)) {
            throw new IllegalArgumentException("Rating must be between 0 and 5");
        }
        if (totalDeliveries < 0) {
            throw new IllegalArgumentException("Total deliveries cannot be negative");
        }
        if (createdAt == null) {
            createdAt = Instant.now();
        }
        if (updatedAt == null) {
            updatedAt = createdAt;
        }
    }
    
    public static DeliveryPerson create(
        String name,
        String email,
        String phone,
        String documentNumber,
        VehicleInfo vehicle,
        WorkingHours workingHours
    ) {
        return new DeliveryPerson(
            DeliveryPersonId.generate(),
            name,
            email,
            phone,
            documentNumber,
            vehicle,
            DeliveryPersonStatus.OFFLINE,
            null,
            workingHours,
            null,
            0,
            Instant.now(),
            Instant.now()
        );
    }
    
    public DeliveryPerson goOnline(Location location) {
        if (status == DeliveryPersonStatus.ONLINE) {
            throw new IllegalStateException("Delivery person is already online");
        }
        if (status == DeliveryPersonStatus.SUSPENDED) {
            throw new IllegalStateException("Suspended delivery person cannot go online");
        }
        if (!workingHours.isWorkingNow()) {
            throw new IllegalStateException("Cannot go online outside working hours");
        }
        
        return new DeliveryPerson(
            id, name, email, phone, documentNumber, vehicle,
            DeliveryPersonStatus.ONLINE, location, workingHours,
            rating, totalDeliveries, createdAt, Instant.now()
        );
    }
    
    public DeliveryPerson goOffline() {
        if (status == DeliveryPersonStatus.BUSY) {
            throw new IllegalStateException("Cannot go offline while busy with delivery");
        }
        
        return new DeliveryPerson(
            id, name, email, phone, documentNumber, vehicle,
            DeliveryPersonStatus.OFFLINE, currentLocation, workingHours,
            rating, totalDeliveries, createdAt, Instant.now()
        );
    }
    
    public DeliveryPerson updateLocation(Location newLocation) {
        if (status == DeliveryPersonStatus.OFFLINE) {
            throw new IllegalStateException("Cannot update location while offline");
        }
        
        return new DeliveryPerson(
            id, name, email, phone, documentNumber, vehicle,
            status, newLocation, workingHours,
            rating, totalDeliveries, createdAt, Instant.now()
        );
    }
    
    public DeliveryPerson startDelivery() {
        if (status != DeliveryPersonStatus.ONLINE) {
            throw new IllegalStateException("Delivery person must be online to start delivery");
        }
        
        return new DeliveryPerson(
            id, name, email, phone, documentNumber, vehicle,
            DeliveryPersonStatus.BUSY, currentLocation, workingHours,
            rating, totalDeliveries, createdAt, Instant.now()
        );
    }
    
    public DeliveryPerson completeDelivery(BigDecimal newRating) {
        if (status != DeliveryPersonStatus.BUSY) {
            throw new IllegalStateException("No active delivery to complete");
        }
        
        var newTotalDeliveries = totalDeliveries + 1;
        var updatedRating = calculateNewRating(newRating, newTotalDeliveries);
        
        return new DeliveryPerson(
            id, name, email, phone, documentNumber, vehicle,
            DeliveryPersonStatus.ONLINE, currentLocation, workingHours,
            updatedRating, newTotalDeliveries, createdAt, Instant.now()
        );
    }
    
    public DeliveryPerson suspend(String reason) {
        return new DeliveryPerson(
            id, name, email, phone, documentNumber, vehicle,
            DeliveryPersonStatus.SUSPENDED, currentLocation, workingHours,
            rating, totalDeliveries, createdAt, Instant.now()
        );
    }
    
    public boolean isAvailableForDelivery() {
        return status == DeliveryPersonStatus.ONLINE && 
               workingHours.isWorkingNow() && 
               currentLocation != null;
    }
    
    public double distanceToLocation(Location targetLocation) {
        if (currentLocation == null || targetLocation == null) {
            return Double.MAX_VALUE;
        }
        return currentLocation.distanceTo(targetLocation);
    }
    
    private BigDecimal calculateNewRating(BigDecimal newRating, int newTotalDeliveries) {
        if (rating == null) {
            return newRating;
        }
        
        var totalRatingPoints = rating.multiply(BigDecimal.valueOf(totalDeliveries));
        var newTotalRatingPoints = totalRatingPoints.add(newRating);
        
        return newTotalRatingPoints.divide(BigDecimal.valueOf(newTotalDeliveries), 2, BigDecimal.ROUND_HALF_UP);
    }
}

