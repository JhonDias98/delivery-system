package com.delivery.orders.domain.port;

import com.delivery.orders.domain.event.saga.*;
import com.delivery.orders.domain.event.order.*;

public interface SagaEventPublisher {
    
    void publishRestaurantValidationRequested(RestaurantValidationRequestedEvent event);
    
    void publishDeliveryAllocationRequested(DeliveryAllocationRequestedEvent event);
    
    void publishPaymentProcessingRequested(PaymentProcessingRequestedEvent event);
    
    void publishRestaurantCompensationRequested(RestaurantCompensationRequestedEvent event);
    
    void publishDeliveryCompensationRequested(DeliveryCompensationRequestedEvent event);
    
    void publishPaymentCompensationRequested(PaymentCompensationRequestedEvent event);
    
    void publishOrderConfirmed(OrderConfirmedEvent event);
    
    void publishOrderSagaFailed(OrderSagaFailedEvent event);
}

