package com.example.order.controller.dto;

public record CreateOrderRequest(
        String productName,
        int price,
        int quantity
) {
}