package com.delivery.payments.domain.model;

public enum PaymentMethod {
    CREDIT_CARD("Credit Card"),
    DEBIT_CARD("Debit Card"),
    PIX("PIX"),
    CASH("Cash"),
    DIGITAL_WALLET("Digital Wallet");
    
    private final String displayName;
    
    PaymentMethod(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public boolean isDigital() {
        return this != CASH;
    }
    
    public boolean requiresOnlineProcessing() {
        return this == CREDIT_CARD || this == DEBIT_CARD || this == PIX || this == DIGITAL_WALLET;
    }
}

