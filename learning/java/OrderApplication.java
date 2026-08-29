package com.example.order.console;

import com.example.order.domain.Order;
import com.example.order.service.OrderService;

import java.util.ArrayList;
import java.util.List;

public class OrderApplication {

    public static void main(String[] args) {
        List<Order> orders = new ArrayList<>();

        Order keyboardOrder = new Order("키보드", 50_000, 2);
        Order mouseOrder = new Order("마우스", 30_000, 1);
        Order monitorOrder = new Order("모니터", 200_000, 1);

        orders.add(keyboardOrder);
        orders.add(mouseOrder);
        orders.add(monitorOrder);

        for (Order order : orders) {
            int finalPrice = order.calculateFinalPrice();
            System.out.println(order.getProductName() + ": " + finalPrice);
        }

        OrderService orderService = new OrderService();
        int totalPaymentAmount =
                orderService.calculateTotalPaymentAmount(orders);

        System.out.println("총 합계: " + totalPaymentAmount);
    }
}
