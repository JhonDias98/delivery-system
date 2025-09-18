package com.delivery.delivery.domain.port;

import com.delivery.delivery.domain.model.Delivery;
import com.delivery.delivery.domain.model.DeliveryId;
import com.delivery.delivery.domain.model.DeliveryPersonId;
import com.delivery.delivery.domain.model.DeliveryStatus;

import java.util.List;
import java.util.Optional;

public interface DeliveryRepository {

    Delivery save(Delivery delivery);
    
    Optional<Delivery> findById(DeliveryId id);

    Optional<Delivery> findByOrderId(String orderId);

    List<Delivery> findByStatus(DeliveryStatus status);

    List<Delivery> findActiveDeliveriesByDeliveryPerson(DeliveryPersonId deliveryPersonId);

    long countActiveDeliveriesByDeliveryPerson(DeliveryPersonId deliveryPersonId);

    List<Delivery> findByDeliveryPersonId(DeliveryPersonId deliveryPersonId);

    List<Delivery> findAll();

    void delete(DeliveryId id);

    boolean existsById(DeliveryId id);
}

