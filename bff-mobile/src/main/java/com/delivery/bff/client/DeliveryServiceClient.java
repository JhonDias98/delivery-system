package com.delivery.bff.client;

import com.delivery.bff.dto.DeliveryDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
public class DeliveryServiceClient {
    
    private final RestTemplate restTemplate;
    private final String deliveryServiceUrl;
    
    public DeliveryServiceClient(
        RestTemplate restTemplate,
        @Value("${services.delivery.url:http://localhost:8082}") String deliveryServiceUrl
    ) {
        this.restTemplate = restTemplate;
        this.deliveryServiceUrl = deliveryServiceUrl;
    }
    
    public Optional<DeliveryDto> findByOrderId(String orderId) {
        try {
            var delivery = restTemplate.getForObject(
                deliveryServiceUrl + "/api/deliveries/order/" + orderId,
                DeliveryDto.class
            );
            return Optional.ofNullable(delivery);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
    
    public Optional<DeliveryDto> findById(String deliveryId) {
        try {
            var delivery = restTemplate.getForObject(
                deliveryServiceUrl + "/api/deliveries/" + deliveryId,
                DeliveryDto.class
            );
            return Optional.ofNullable(delivery);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}

