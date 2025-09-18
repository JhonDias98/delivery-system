package com.delivery.delivery.domain.service;

import com.delivery.delivery.domain.event.*;
import com.delivery.delivery.domain.exception.DeliveryPersonNotFoundException;
import com.delivery.delivery.domain.model.*;
import com.delivery.delivery.domain.port.*;
import com.delivery.delivery.domain.valueobject.DeliveryAllocation;
import com.delivery.delivery.domain.valueobject.ScoredDeliveryPerson;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class DeliveryAllocationService {
    
    private final DeliveryPersonRepository deliveryPersonRepository;
    private final DeliveryRepository deliveryRepository;
    private final EventPublisher eventPublisher;
    private final CacheService cacheService;

    private static final double MAX_ALLOCATION_RADIUS_KM = 10.0;
    private static final int MAX_CONCURRENT_DELIVERIES = 3;
    
    public DeliveryAllocationService(
        DeliveryPersonRepository deliveryPersonRepository,
        DeliveryRepository deliveryRepository,
        EventPublisher eventPublisher,
        CacheService cacheService
    ) {
        this.deliveryPersonRepository = deliveryPersonRepository;
        this.deliveryRepository = deliveryRepository;
        this.eventPublisher = eventPublisher;
        this.cacheService = cacheService;
    }

    public Optional<DeliveryAllocation> allocateDeliveryPerson(
        String orderId,
        String restaurantId,
        Location pickupLocation,
        Location deliveryLocation,
        BigDecimal orderValue
    ) {
        var availableDeliveryPersons = findAvailableDeliveryPersons(pickupLocation);
        
        if (availableDeliveryPersons.isEmpty()) {
            publishAllocationFailed(orderId, "No delivery persons available in the area");
            return Optional.empty();
        }
        
        var bestMatch = findBestMatch(availableDeliveryPersons, pickupLocation, deliveryLocation, orderValue);
        
        if (bestMatch.isEmpty()) {
            publishAllocationFailed(orderId, "No suitable delivery person found");
            return Optional.empty();
        }
        
        var selectedDeliveryPerson = bestMatch.get();
        
        var delivery = Delivery.create(
            orderId,
            selectedDeliveryPerson.id(),
            restaurantId,
            pickupLocation,
            deliveryLocation,
            null
        );
        
        var busyDeliveryPerson = selectedDeliveryPerson.startDelivery();
        
        var savedDelivery = deliveryRepository.save(delivery);
        var savedDeliveryPerson = deliveryPersonRepository.save(busyDeliveryPerson);
        
        cacheService.cacheDelivery(savedDelivery);
        cacheService.cacheDeliveryPerson(savedDeliveryPerson);
        
        var allocation = new DeliveryAllocation(
            savedDelivery.id().toString(),
            savedDeliveryPerson.id().toString(),
            savedDeliveryPerson.name(),
            savedDeliveryPerson.vehicle(),
            savedDeliveryPerson.currentLocation(),
            savedDelivery.estimatedPickupTime(),
            savedDelivery.estimatedDeliveryTime()
        );
        
        publishAllocationSuccessful(orderId, allocation);
        
        return Optional.of(allocation);
    }

    private List<DeliveryPerson> findAvailableDeliveryPersons(Location pickupLocation) {
        var cacheKey = String.format("available_delivery_persons_%s_%s", 
            pickupLocation.latitude(), pickupLocation.longitude());
        
        var cached = cacheService.getCachedDeliveryPersonList(cacheKey);
        if (cached.isPresent()) {
            return cached.get();
        }
        
        var allAvailable = deliveryPersonRepository.findAvailableDeliveryPersons();
        
        var nearbyDeliveryPersons = allAvailable.stream()
            .filter(dp -> dp.currentLocation() != null)
            .filter(dp -> dp.currentLocation().isWithinRadius(pickupLocation, MAX_ALLOCATION_RADIUS_KM))
            .filter(this::hasCapacityForNewDelivery)
            .toList();
        
        cacheService.cacheDeliveryPersonList(cacheKey, nearbyDeliveryPersons,
            java.time.Duration.ofMinutes(2));
        
        return nearbyDeliveryPersons;
    }

    private Optional<DeliveryPerson> findBestMatch(
        List<DeliveryPerson> candidates,
        Location pickupLocation,
        Location deliveryLocation,
        BigDecimal orderValue
    ) {
        return candidates.stream()
            .map(dp -> new ScoredDeliveryPerson(dp, calculateScore(dp, pickupLocation, deliveryLocation, orderValue)))
            .max(Comparator.comparing(ScoredDeliveryPerson::score))
            .map(ScoredDeliveryPerson::deliveryPerson);
    }

    private double calculateScore(
        DeliveryPerson deliveryPerson,
        Location pickupLocation,
        Location deliveryLocation,
        BigDecimal orderValue
    ) {
        var score = 0.0;
        
        var distanceToPickup = deliveryPerson.distanceToLocation(pickupLocation);
        var distanceScore = Math.max(0, 100 - (distanceToPickup * 10));
        score += distanceScore * 0.4;

        if (deliveryPerson.rating() != null) {
            var ratingScore = deliveryPerson.rating().doubleValue() * 20;
            score += ratingScore * 0.3;
        }

        var experienceScore = Math.min(100, deliveryPerson.totalDeliveries() * 2);
        score += experienceScore * 0.2;

        var vehicleScore = switch (deliveryPerson.vehicle().type()) {
            case MOTORCYCLE -> 100;
            case SCOOTER -> 80;
            case BICYCLE -> 60;
            case CAR -> 40;
            case WALKING -> 20;
        };
        score += vehicleScore * 0.1;
        
        return score;
    }

    private boolean hasCapacityForNewDelivery(DeliveryPerson deliveryPerson) {
        var activeDeliveries = deliveryRepository.countActiveDeliveriesByDeliveryPerson(deliveryPerson.id());
        return activeDeliveries < MAX_CONCURRENT_DELIVERIES;
    }

    public DeliveryPerson updateDeliveryPersonLocation(DeliveryPersonId id, Location newLocation) {
        var deliveryPerson = deliveryPersonRepository.findById(id)
            .orElseThrow(() -> new DeliveryPersonNotFoundException("Delivery person not found: " + id));
        
        var updatedDeliveryPerson = deliveryPerson.updateLocation(newLocation);
        var savedDeliveryPerson = deliveryPersonRepository.save(updatedDeliveryPerson);

        cacheService.cacheDeliveryPerson(savedDeliveryPerson);

        updateActiveDeliveriesLocation(id, newLocation);

        var event = new DeliveryPersonLocationUpdatedEvent(
            savedDeliveryPerson.id().toString(),
            newLocation,
            java.time.Instant.now()
        );
        eventPublisher.publishDeliveryPersonLocationUpdated(event);
        
        return savedDeliveryPerson;
    }
    
    private void updateActiveDeliveriesLocation(DeliveryPersonId deliveryPersonId, Location newLocation) {
        var activeDeliveries = deliveryRepository.findActiveDeliveriesByDeliveryPerson(deliveryPersonId);
        
        for (var delivery : activeDeliveries) {
            var updatedDelivery = delivery.updateLocation(newLocation);
            var savedDelivery = deliveryRepository.save(updatedDelivery);

            cacheService.cacheDelivery(savedDelivery);

            var event = new DeliveryLocationUpdatedEvent(
                savedDelivery.id().toString(),
                savedDelivery.orderId(),
                newLocation,
                savedDelivery.status(),
                java.time.Instant.now()
            );
            eventPublisher.publishDeliveryLocationUpdated(event);
        }
    }
    
    private void publishAllocationSuccessful(String orderId, DeliveryAllocation allocation) {
        var event = new DeliveryAllocationSuccessfulEvent(
            orderId,
            allocation.deliveryPersonId(),
            allocation.deliveryPersonName(),
            allocation.estimatedPickupTime(),
            allocation.estimatedDeliveryTime(),
            java.time.Instant.now()
        );
        eventPublisher.publishDeliveryAllocationSuccessful(event);
    }
    
    private void publishAllocationFailed(String orderId, String reason) {
        var event = new DeliveryAllocationFailedEvent(
            orderId,
            reason,
            java.time.Instant.now()
        );
        eventPublisher.publishDeliveryAllocationFailed(event);
    }
}

