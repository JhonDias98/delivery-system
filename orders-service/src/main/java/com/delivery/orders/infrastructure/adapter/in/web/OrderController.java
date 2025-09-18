package com.delivery.orders.infrastructure.adapter.in.web;

import com.delivery.orders.application.dto.CreateOrderCommand;
import com.delivery.orders.application.dto.OrderResponse;
import com.delivery.orders.application.usecase.CreateOrderUseCase;
import com.delivery.orders.application.usecase.GetOrderUseCase;
import com.delivery.orders.application.usecase.CancelOrderUseCase;
import com.delivery.orders.infrastructure.adapter.in.web.dto.CreateOrderRequest;
import com.delivery.orders.infrastructure.adapter.in.web.mapper.OrderWebMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/orders")
@CrossOrigin(origins = "*")
public class OrderController {
    
    private final CreateOrderUseCase createOrderUseCase;
    private final GetOrderUseCase getOrderUseCase;
    private final CancelOrderUseCase cancelOrderUseCase;
    private final OrderWebMapper orderWebMapper;
    
    public OrderController(
        CreateOrderUseCase createOrderUseCase,
        GetOrderUseCase getOrderUseCase,
        CancelOrderUseCase cancelOrderUseCase,
        OrderWebMapper orderWebMapper
    ) {
        this.createOrderUseCase = createOrderUseCase;
        this.getOrderUseCase = getOrderUseCase;
        this.cancelOrderUseCase = cancelOrderUseCase;
        this.orderWebMapper = orderWebMapper;
    }
    
    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        CreateOrderCommand command = orderWebMapper.toCommand(request);
        OrderResponse response = createOrderUseCase.execute(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable String orderId) {
        OrderResponse response = getOrderUseCase.execute(orderId);
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<OrderResponse> cancelOrder(@PathVariable String orderId) {
        OrderResponse response = cancelOrderUseCase.execute(orderId);
        return ResponseEntity.ok(response);
    }
}

