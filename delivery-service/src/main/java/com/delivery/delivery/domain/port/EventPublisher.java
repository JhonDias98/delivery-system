package com.delivery.delivery.domain.port;

import com.delivery.delivery.domain.event.*;

public interface EventPublisher {

    void publishDeliveryAllocationSuccessful(DeliveryAllocationSuccessfulEvent event);

    void publishDeliveryAllocationFailed(DeliveryAllocationFailedEvent event);

    void publishDeliveryPersonLocationUpdated(DeliveryPersonLocationUpdatedEvent event);

    void publishDeliveryLocationUpdated(DeliveryLocationUpdatedEvent event);
}

