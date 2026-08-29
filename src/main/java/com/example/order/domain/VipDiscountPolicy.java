package com.example.order.domain;

public class VipDiscountPolicy implements DiscountPolicy {

    @Override
    public int calculateDiscount(int totalPrice) {
        return totalPrice * 20 / 100;
    }
}
