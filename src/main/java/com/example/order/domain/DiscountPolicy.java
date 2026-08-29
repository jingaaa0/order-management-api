package com.example.order.domain;

public interface DiscountPolicy {

    int calculateDiscount(int totalPrice);
}
