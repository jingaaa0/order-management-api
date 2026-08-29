package com.example.order.controller;

import com.example.order.domain.Order;
import com.example.order.service.OrderService;
import com.example.order.controller.dto.CreateOrderRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;
import java.util.List;

@RestController // 반환값 -> HTTP / JSON
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<Order> getOrders() {
        // OrderService의 전체 주문 조회 결과 반환
        return orderService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order createOrder(@RequestBody CreateOrderRequest request) {
        // request의 세 값을 Service에 전달
        // 생성된 Order 반환
        return orderService.createOrder(
                    request.productName(),
                    request.price(),
                    request.quantity()
        );
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(
            @PathVariable("id") UUID id
    ) {
        Order order = orderService.findById(id);

        if (order == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(order);
    }
}