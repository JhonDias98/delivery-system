package com.delivery.delivery.domain.model;

public enum DeliveryPersonStatus {
    ONLINE("Online"),
    AVAILABLE("Available"),
    BUSY("Busy"),
    OFFLINE("Offline"),
    ON_BREAK("On Break"),
    SUSPENDED("Suspended");
    
    private final String displayName;
    
    DeliveryPersonStatus(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public boolean isAvailableForDelivery() {
        return this == ONLINE || this == AVAILABLE;
    }
    
    public boolean isActive() {
        return this != OFFLINE && this != SUSPENDED;
    }
}

