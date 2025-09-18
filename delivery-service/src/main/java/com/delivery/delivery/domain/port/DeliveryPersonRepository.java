package com.delivery.delivery.domain.port;

import com.delivery.delivery.domain.model.DeliveryPerson;
import com.delivery.delivery.domain.model.DeliveryPersonId;

import java.util.List;
import java.util.Optional;

public interface DeliveryPersonRepository {

    DeliveryPerson save(DeliveryPerson deliveryPerson);

    Optional<DeliveryPerson> findById(DeliveryPersonId id);

    List<DeliveryPerson> findAvailableDeliveryPersons();

    List<DeliveryPerson> findByStatus(com.delivery.delivery.domain.model.DeliveryPersonStatus status);

    List<DeliveryPerson> findAll();

    void delete(DeliveryPersonId id);

    boolean existsById(DeliveryPersonId id);
}

