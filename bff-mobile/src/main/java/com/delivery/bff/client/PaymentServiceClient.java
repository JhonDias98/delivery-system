package com.delivery.bff.client;

import com.delivery.bff.dto.PaymentDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
public class PaymentServiceClient {
    
    private final RestTemplate restTemplate;
    private final String paymentsServiceUrl;
    
    public PaymentServiceClient(
        RestTemplate restTemplate,
        @Value("${services.payments.url:http://localhost:8083}") String paymentsServiceUrl
    ) {
        this.restTemplate = restTemplate;
        this.paymentsServiceUrl = paymentsServiceUrl;
    }
    
    public Optional<PaymentDto> findByOrderId(String orderId) {
        try {
            var payment = restTemplate.getForObject(
                paymentsServiceUrl + "/api/payments/order/" + orderId,
                PaymentDto.class
            );
            return Optional.ofNullable(payment);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
    
    public Optional<PaymentDto> findById(String paymentId) {
        try {
            var payment = restTemplate.getForObject(
                paymentsServiceUrl + "/api/payments/" + paymentId,
                PaymentDto.class
            );
            return Optional.ofNullable(payment);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}

