package com.delivery.payments.domain.port;

import com.delivery.payments.domain.model.Payment;

import java.util.Optional;

public interface CacheService {

    void cachePayment(Payment payment);

    Optional<Payment> getCachedPayment(String paymentId);

    Optional<Payment> getCachedPaymentByOrderId(String orderId);

    void evict(String key);

    void clear();
}

