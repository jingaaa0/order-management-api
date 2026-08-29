package com.example.order.service;

import com.example.order.domain.Order;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    private final List<Order> orders = new ArrayList<>();

    public int calculateTotalPaymentAmount(List<Order> orders) {
        int totalPaymentAmount = 0;

        for (Order order : orders) {
            totalPaymentAmount += order.calculateFinalPrice();
        }

        return totalPaymentAmount;
    }

    public Order findOrderByProductName(
            List<Order> orders,
            String productName
    ) {
        for (Order order : orders) {
            if (order.getProductName().equals(productName)) {
                return order;
            }
        }

        return null;
    }

    public List<Order> findAll() {
        return List.copyOf(orders);
    }

    public Order createOrder(
        String productName,
        int price,
        int quantity
    ) {
        Order order = new Order(productName, price, quantity);
        orders.add(order);
        return order;
    }

    public Order findById(UUID id) {
        // 내부 orders 목록 순회
        for (Order order : orders) {
            // order.getId().equals(id)이면 즉시 반환
            if (order.getId().equals(id)) {
                return order;
            }
        }
        return null;
    }
}
