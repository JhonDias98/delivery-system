package com.delivery.bff.dto;

import java.math.BigDecimal;
import java.time.Instant;

public record PaymentDto(
    String id,
    String orderId,
    String customerId,
    BigDecimal amount,
    String method,
    String status,
    String transactionId,
    Instant createdAt,
    Instant processedAt,
    Instant updatedAt
) {}

