package com.example.order.service;

import com.example.order.domain.Order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class OrderServiceTest {
    private OrderService orderService;
    private List<Order> orders;

    @BeforeEach
    void setUp() {
        orderService = new OrderService();
        orders = List.of(
                new Order("키보드", 50_000, 2),
                new Order("마우스", 30_000, 1),
                new Order("모니터", 200_000, 1)
        );
    }

    @Test
    void calculatesTotalPaymentAmount() {
        int totalPaymentAmount =
                orderService.calculateTotalPaymentAmount(orders);

        assertThat(totalPaymentAmount).isEqualTo(300_000);
    }

    @Test
    void returnsZeroForEmptyOrders() {
        int totalPaymentAmount =
                orderService.calculateTotalPaymentAmount(List.of());

        assertThat(totalPaymentAmount).isEqualTo(0);
    }

    @Test
    void returnsOrderWhenProductNameMatches() {
        Order foundOrder =
                orderService.findOrderByProductName(orders, "마우스");

        assertThat(foundOrder).isNotNull();
        assertThat(foundOrder.getProductName()).isEqualTo("마우스");
    }

    @Test
    void returnsNullWhenProductNameDoesNotExist() {
        Order foundOrder =
                orderService.findOrderByProductName(orders, "스피커");

        assertThat(foundOrder).isNull();
    }

    @Test
    void createsAndStoresOrder() {
        Order createdOrder =
                orderService.createOrder("키보드", 50_000, 2);

        assertThat(createdOrder.getProductName()).isEqualTo("키보드");
        assertThat(createdOrder.getPrice()).isEqualTo(50_000);
        assertThat(createdOrder.getQuantity()).isEqualTo(2);
        assertThat(orderService.findAll()).containsExactly(createdOrder);
    }

    @Test
    void findsOrderById() {
        Order createdOrder =
                orderService.createOrder("키보드", 50_000, 2);

        Order foundOrder =
                orderService.findById(createdOrder.getId());

        assertThat(foundOrder).isSameAs(createdOrder);
    }

    @Test
    void returnsNullWhenOrderIdDoesNotExist() {
        Order foundOrder = orderService.findById(UUID.randomUUID());

        assertThat(foundOrder).isNull();
    }
}
