package com.example.order.domain;

import java.util.UUID;

public class Order {

    private final String productName;
    private final int price;
    private final int quantity;
    private final UUID id;

    private OrderStatus status;
    private final DiscountPolicy discountPolicy;

    public Order(String productName, int price, int quantity) {
        this(productName, price, quantity, new RegularDiscountPolicy());
    }

    public Order(String productName, int price, int quantity, DiscountPolicy discountPolicy) {

        if (price <= 0) {
            throw new IllegalArgumentException("가격은 0보다 커야 합니다.");
        }

        if (quantity <= 0) {
            throw new IllegalArgumentException("수량은 1 이상이어야 합니다.");
        }

        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.discountPolicy = discountPolicy;
        this.id = UUID.randomUUID();
        this.status = OrderStatus.CREATED;
    }

    public int calculateTotalPrice() {
        return price * quantity;
    }

    public int calculateDiscount() {
        int totalPrice = calculateTotalPrice();
        return discountPolicy.calculateDiscount(totalPrice);
    }

    public int calculateFinalPrice() {
        int totalPrice = calculateTotalPrice();
        int discountAmount = calculateDiscount();
        return totalPrice - discountAmount;
    }

    public String getProductName() {
        return productName;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void pay() {
        if (status != OrderStatus.CREATED) {
            throw new InvalidOrderStateException("CREATED 상태의 주문만 결제할 수 있습니다. 현재 상태: " + status);
        }

        this.status = OrderStatus.PAID;
    }

    public void cancel() {
        this.status = OrderStatus.CANCELLED;
    }

    public UUID getId() {
        return id;
    }
}
