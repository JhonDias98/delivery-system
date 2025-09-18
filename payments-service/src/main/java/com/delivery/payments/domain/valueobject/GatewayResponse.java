package com.delivery.payments.domain.valueobject;

public record GatewayResponse(
    String transactionId,
    String response,
    boolean success,
    String errorMessage
) {
    
    public GatewayResponse {
        if (transactionId == null || transactionId.isBlank()) {
            throw new IllegalArgumentException("Transaction ID cannot be null or blank");
        }
        if (response == null || response.isBlank()) {
            throw new IllegalArgumentException("Response cannot be null or blank");
        }
    }
    
    public static GatewayResponse success(String transactionId, String response) {
        return new GatewayResponse(transactionId, response, true, null);
    }
    
    public static GatewayResponse failure(String transactionId, String response, String errorMessage) {
        return new GatewayResponse(transactionId, response, false, errorMessage);
    }
}

