package com.delivery.payments.domain.port;

import com.delivery.payments.domain.model.Payment;
import com.delivery.payments.domain.model.PaymentId;
import com.delivery.payments.domain.model.PaymentStatus;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository {

    Payment save(Payment payment);

    Optional<Payment> findById(PaymentId id);

    Optional<Payment> findByOrderId(String orderId);

    List<Payment> findByCustomerId(String customerId);

    List<Payment> findByStatus(PaymentStatus status);

    List<Payment> findAll();

    void delete(PaymentId id);

    boolean existsById(PaymentId id);
}

