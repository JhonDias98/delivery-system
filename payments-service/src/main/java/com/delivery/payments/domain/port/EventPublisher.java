package com.delivery.payments.domain.port;

import com.delivery.payments.domain.event.*;

public interface EventPublisher {

    void publishPaymentCreated(PaymentCreatedEvent event);

    void publishPaymentApproved(PaymentApprovedEvent event);

    void publishPaymentFailed(PaymentFailedEvent event);

    void publishPaymentRefunded(PaymentRefundedEvent event);
}

