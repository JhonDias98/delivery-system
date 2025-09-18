package com.delivery.bff.controller;

import com.delivery.bff.dto.CreateOrderRequest;
import com.delivery.bff.dto.OrderResponse;
import com.delivery.bff.service.OrderAggregationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mobile/orders")
@CrossOrigin(origins = "*")
public class OrderController {
    
    private final OrderAggregationService orderAggregationService;
    
    public OrderController(OrderAggregationService orderAggregationService) {
        this.orderAggregationService = orderAggregationService;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody CreateOrderRequest request) {
        var orderResponse = orderAggregationService.createOrder(request);
        return ResponseEntity.ok(orderResponse);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<OrderResponse>> getCustomerOrders(@PathVariable String customerId) {
        var orders = orderAggregationService.getCustomerOrders(customerId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderDetails(@PathVariable String orderId) {
        var orderResponse = orderAggregationService.getOrderDetails(orderId);
        return ResponseEntity.ok(orderResponse);
    }

    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<Void> cancelOrder(@PathVariable String orderId) {
        orderAggregationService.cancelOrder(orderId);
        return ResponseEntity.ok().build();
    }
}

