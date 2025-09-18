package com.delivery.payments.domain.valueobject;

import java.math.BigDecimal;

public record RefundResponse(
    String refundId,
    String originalTransactionId,
    BigDecimal refundAmount,
    boolean success,
    String errorMessage
) {
    
    public RefundResponse {
        if (refundId == null || refundId.isBlank()) {
            throw new IllegalArgumentException("Refund ID cannot be null or blank");
        }
        if (originalTransactionId == null || originalTransactionId.isBlank()) {
            throw new IllegalArgumentException("Original transaction ID cannot be null or blank");
        }
        if (refundAmount == null || refundAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Refund amount must be positive");
        }
    }
    
    public static RefundResponse success(String refundId, String originalTransactionId, BigDecimal refundAmount) {
        return new RefundResponse(refundId, originalTransactionId, refundAmount, true, null);
    }
    
    public static RefundResponse failure(String refundId, String originalTransactionId, BigDecimal refundAmount, String errorMessage) {
        return new RefundResponse(refundId, originalTransactionId, refundAmount, false, errorMessage);
    }
}

