package com.delivery.delivery.domain.model;

public enum DeliveryStatus {
    ASSIGNED("Assigned"),
    GOING_TO_PICKUP("Going to Pickup"),
    AT_PICKUP("At Pickup"),
    IN_TRANSIT("In Transit"),
    AT_DELIVERY("At Delivery"),
    DELIVERED("Delivered"),
    CANCELLED("Cancelled");
    
    private final String displayName;
    
    DeliveryStatus(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public boolean isActive() {
        return this != DELIVERED && this != CANCELLED;
    }
    
    public boolean isCompleted() {
        return this == DELIVERED || this == CANCELLED;
    }
}

