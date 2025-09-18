package com.delivery.delivery.domain.model;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public record Delivery(
    DeliveryId id,
    String orderId,
    DeliveryPersonId deliveryPersonId,
    String restaurantId,
    Location pickupLocation,
    Location deliveryLocation,
    DeliveryStatus status,
    List<DeliveryEvent> events,
    Instant estimatedPickupTime,
    Instant estimatedDeliveryTime,
    Instant actualPickupTime,
    Instant actualDeliveryTime,
    BigDecimal distance,
    Duration estimatedDuration,
    String specialInstructions,
    Instant createdAt,
    Instant updatedAt
) {
    
    public Delivery {
        if (id == null) {
            throw new IllegalArgumentException("Delivery ID cannot be null");
        }
        if (orderId == null || orderId.isBlank()) {
            throw new IllegalArgumentException("Order ID cannot be null or blank");
        }
        if (deliveryPersonId == null) {
            throw new IllegalArgumentException("Delivery person ID cannot be null");
        }
        if (restaurantId == null || restaurantId.isBlank()) {
            throw new IllegalArgumentException("Restaurant ID cannot be null or blank");
        }
        if (pickupLocation == null) {
            throw new IllegalArgumentException("Pickup location cannot be null");
        }
        if (deliveryLocation == null) {
            throw new IllegalArgumentException("Delivery location cannot be null");
        }
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }
        if (events == null) {
            events = new ArrayList<>();
        }
        if (distance != null && distance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Distance cannot be negative");
        }
        if (createdAt == null) {
            createdAt = Instant.now();
        }
        if (updatedAt == null) {
            updatedAt = createdAt;
        }
    }
    
    public static Delivery create(
        String orderId,
        DeliveryPersonId deliveryPersonId,
        String restaurantId,
        Location pickupLocation,
        Location deliveryLocation,
        String specialInstructions
    ) {
        var distance = BigDecimal.valueOf(pickupLocation.distanceTo(deliveryLocation));
        var estimatedDuration = calculateEstimatedDuration(distance);
        var now = Instant.now();
        
        var delivery = new Delivery(
            DeliveryId.generate(),
            orderId,
            deliveryPersonId,
            restaurantId,
            pickupLocation,
            deliveryLocation,
            DeliveryStatus.ASSIGNED,
            new ArrayList<>(),
            now.plus(Duration.ofMinutes(15)),
            now.plus(estimatedDuration).plus(Duration.ofMinutes(15)),
            null,
            null,
            distance,
            estimatedDuration,
            specialInstructions,
            now,
            now
        );
        
        return delivery.addEvent(DeliveryEventType.ASSIGNED, "Delivery assigned to delivery person");
    }
    
    public Delivery startPickup(Location currentLocation) {
        if (status != DeliveryStatus.ASSIGNED) {
            throw new IllegalStateException("Can only start pickup from ASSIGNED status");
        }
        
        return updateStatus(DeliveryStatus.GOING_TO_PICKUP)
            .addEvent(DeliveryEventType.GOING_TO_PICKUP, 
                "Delivery person is going to pickup location", currentLocation);
    }
    
    public Delivery arriveAtPickup(Location currentLocation) {
        if (status != DeliveryStatus.GOING_TO_PICKUP) {
            throw new IllegalStateException("Can only arrive at pickup from GOING_TO_PICKUP status");
        }
        
        return updateStatus(DeliveryStatus.AT_PICKUP)
            .addEvent(DeliveryEventType.ARRIVED_AT_PICKUP, 
                "Arrived at pickup location", currentLocation);
    }
    
    public Delivery pickupOrder(Location currentLocation) {
        if (status != DeliveryStatus.AT_PICKUP) {
            throw new IllegalStateException("Can only pickup from AT_PICKUP status");
        }
        
        return new Delivery(
            id, orderId, deliveryPersonId, restaurantId, pickupLocation, deliveryLocation,
            DeliveryStatus.IN_TRANSIT, events, estimatedPickupTime, estimatedDeliveryTime,
            Instant.now(), actualDeliveryTime, distance, estimatedDuration,
            specialInstructions, createdAt, Instant.now()
        ).addEvent(DeliveryEventType.ORDER_PICKED_UP, 
            "Order picked up from restaurant", currentLocation);
    }
    
    public Delivery arriveAtDelivery(Location currentLocation) {
        if (status != DeliveryStatus.IN_TRANSIT) {
            throw new IllegalStateException("Can only arrive at delivery from IN_TRANSIT status");
        }
        
        return updateStatus(DeliveryStatus.AT_DELIVERY)
            .addEvent(DeliveryEventType.ARRIVED_AT_DELIVERY, 
                "Arrived at delivery location", currentLocation);
    }
    
    public Delivery completeDelivery(Location currentLocation, String completionNotes) {
        if (status != DeliveryStatus.AT_DELIVERY) {
            throw new IllegalStateException("Can only complete from AT_DELIVERY status");
        }
        
        return new Delivery(
            id, orderId, deliveryPersonId, restaurantId, pickupLocation, deliveryLocation,
            DeliveryStatus.DELIVERED, events, estimatedPickupTime, estimatedDeliveryTime,
            actualPickupTime, Instant.now(), distance, estimatedDuration,
            specialInstructions, createdAt, Instant.now()
        ).addEvent(DeliveryEventType.DELIVERED, 
            "Order delivered successfully" + (completionNotes != null ? ": " + completionNotes : ""), 
            currentLocation);
    }
    
    public Delivery cancelDelivery(String reason, Location currentLocation) {
        if (status == DeliveryStatus.DELIVERED || status == DeliveryStatus.CANCELLED) {
            throw new IllegalStateException("Cannot cancel completed or already cancelled delivery");
        }
        
        return updateStatus(DeliveryStatus.CANCELLED)
            .addEvent(DeliveryEventType.CANCELLED, 
                "Delivery cancelled: " + reason, currentLocation);
    }
    
    public Delivery updateLocation(Location currentLocation) {
        if (status == DeliveryStatus.DELIVERED || status == DeliveryStatus.CANCELLED) {
            return this;
        }
        
        return addEvent(DeliveryEventType.LOCATION_UPDATE, 
            "Location updated", currentLocation);
    }
    
    private Delivery updateStatus(DeliveryStatus newStatus) {
        return new Delivery(
            id, orderId, deliveryPersonId, restaurantId, pickupLocation, deliveryLocation,
            newStatus, events, estimatedPickupTime, estimatedDeliveryTime,
            actualPickupTime, actualDeliveryTime, distance, estimatedDuration,
            specialInstructions, createdAt, Instant.now()
        );
    }
    
    private Delivery addEvent(DeliveryEventType eventType, String description) {
        return addEvent(eventType, description, null);
    }
    
    private Delivery addEvent(DeliveryEventType eventType, String description, Location location) {
        var newEvents = new ArrayList<>(events);
        newEvents.add(new DeliveryEvent(
            DeliveryEventId.generate(),
            eventType,
            description,
            location,
            Instant.now()
        ));
        
        return new Delivery(
            id, orderId, deliveryPersonId, restaurantId, pickupLocation, deliveryLocation,
            status, newEvents, estimatedPickupTime, estimatedDeliveryTime,
            actualPickupTime, actualDeliveryTime, distance, estimatedDuration,
            specialInstructions, createdAt, Instant.now()
        );
    }
    
    public boolean isActive() {
        return status != DeliveryStatus.DELIVERED && status != DeliveryStatus.CANCELLED;
    }
    
    public boolean isDelayed() {
        if (estimatedDeliveryTime == null) {
            return false;
        }
        
        var now = Instant.now();
        return now.isAfter(estimatedDeliveryTime) && isActive();
    }
    
    public Duration getActualDuration() {
        if (actualPickupTime == null || actualDeliveryTime == null) {
            return null;
        }
        return Duration.between(actualPickupTime, actualDeliveryTime);
    }
    
    public Location getCurrentLocation() {
        return events.stream()
            .filter(event -> event.location() != null)
            .reduce((first, second) -> second)
            .map(DeliveryEvent::location)
            .orElse(null);
    }
    
    private static Duration calculateEstimatedDuration(BigDecimal distanceKm) {
        var averageSpeedKmh = 25.0;
        var hours = distanceKm.doubleValue() / averageSpeedKmh;
        var minutes = (long) (hours * 60);
        return Duration.ofMinutes(Math.max(minutes, 10));
    }
}