package com.delivery.payments.domain.model;

public enum PaymentStatus {
    PENDING("Pending"),
    APPROVED("Approved"),
    FAILED("Failed"),
    REFUNDED("Refunded");
    
    private final String displayName;
    
    PaymentStatus(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public boolean isCompleted() {
        return this == APPROVED || this == FAILED || this == REFUNDED;
    }
    
    public boolean isSuccessful() {
        return this == APPROVED;
    }
    
    public boolean canBeRefunded() {
        return this == APPROVED;
    }
    
    public boolean canBeRetried() {
        return this == FAILED;
    }
}

