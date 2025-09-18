package com.delivery.delivery.domain.port;

import com.delivery.delivery.domain.model.Delivery;
import com.delivery.delivery.domain.model.DeliveryPerson;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

public interface CacheService {

    void cacheDelivery(Delivery delivery);

    Optional<Delivery> getCachedDelivery(String deliveryId);

    void cacheDeliveryPerson(DeliveryPerson deliveryPerson);

    Optional<DeliveryPerson> getCachedDeliveryPerson(String deliveryPersonId);

    void cacheDeliveryPersonList(String key, List<DeliveryPerson> deliveryPersons, Duration ttl);

    Optional<List<DeliveryPerson>> getCachedDeliveryPersonList(String key);

    void evict(String key);

    void clear();
}

