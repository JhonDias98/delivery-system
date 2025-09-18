package com.delivery.bff.client;

import com.delivery.bff.dto.CreateOrderRequest;
import com.delivery.bff.dto.OrderDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class OrderServiceClient {
    
    private final RestTemplate restTemplate;
    private final String ordersServiceUrl;
    
    public OrderServiceClient(
        RestTemplate restTemplate,
        @Value("${services.orders.url:http://localhost:8081}") String ordersServiceUrl
    ) {
        this.restTemplate = restTemplate;
        this.ordersServiceUrl = ordersServiceUrl;
    }
    
    public OrderDto createOrder(CreateOrderRequest request) {
        return restTemplate.postForObject(
            ordersServiceUrl + "/api/orders",
            request,
            OrderDto.class
        );
    }
    
    public OrderDto findById(String orderId) {
        return restTemplate.getForObject(
            ordersServiceUrl + "/api/orders/" + orderId,
            OrderDto.class
        );
    }
    
    @SuppressWarnings("unchecked")
    public List<OrderDto> findByCustomerId(String customerId) {
        return restTemplate.getForObject(
            ordersServiceUrl + "/api/orders/customer/" + customerId,
            List.class
        );
    }
    
    public void cancelOrder(String orderId) {
        restTemplate.postForObject(
            ordersServiceUrl + "/api/orders/" + orderId + "/cancel",
            null,
            Void.class
        );
    }
}

