package com.delivery.payments.domain.port;

import com.delivery.payments.domain.model.PaymentMethod;
import com.delivery.payments.domain.valueobject.GatewayResponse;
import com.delivery.payments.domain.valueobject.RefundResponse;

import java.math.BigDecimal;

public interface PaymentGateway {

    GatewayResponse processPayment(String paymentId, BigDecimal amount, PaymentMethod method);

    RefundResponse refundPayment(String transactionId, BigDecimal amount);

    String checkTransactionStatus(String transactionId);

    boolean cancelTransaction(String transactionId);
}

