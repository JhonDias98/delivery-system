package com.delivery.bff.service;

import com.delivery.bff.client.OrderServiceClient;
import com.delivery.bff.client.PaymentServiceClient;
import com.delivery.bff.client.DeliveryServiceClient;
import com.delivery.bff.dto.CreateOrderRequest;
import com.delivery.bff.dto.OrderResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderAggregationService {
    
    private final OrderServiceClient orderServiceClient;
    private final PaymentServiceClient paymentServiceClient;
    private final DeliveryServiceClient deliveryServiceClient;
    
    public OrderAggregationService(
        OrderServiceClient orderServiceClient,
        PaymentServiceClient paymentServiceClient,
        DeliveryServiceClient deliveryServiceClient
    ) {
        this.orderServiceClient = orderServiceClient;
        this.paymentServiceClient = paymentServiceClient;
        this.deliveryServiceClient = deliveryServiceClient;
    }

    public OrderResponse createOrder(CreateOrderRequest request) {
        var order = orderServiceClient.createOrder(request);
        
        var payment = paymentServiceClient.findByOrderId(order.id())
            .orElse(null);
        
        var delivery = deliveryServiceClient.findByOrderId(order.id())
            .orElse(null);
        
        return OrderResponse.from(order, payment, delivery);
    }
    
    public List<OrderResponse> getCustomerOrders(String customerId) {
        var orders = orderServiceClient.findByCustomerId(customerId);
        
        return orders.stream()
            .map(order -> {
                var payment = paymentServiceClient.findByOrderId(order.id()).orElse(null);
                var delivery = deliveryServiceClient.findByOrderId(order.id()).orElse(null);
                return OrderResponse.from(order, payment, delivery);
            })
            .toList();
    }
    
    public OrderResponse getOrderDetails(String orderId) {
        var order = orderServiceClient.findById(orderId);
        var payment = paymentServiceClient.findByOrderId(orderId).orElse(null);
        var delivery = deliveryServiceClient.findByOrderId(orderId).orElse(null);
        
        return OrderResponse.from(order, payment, delivery);
    }
    
    public void cancelOrder(String orderId) {
        orderServiceClient.cancelOrder(orderId);
    }
}

