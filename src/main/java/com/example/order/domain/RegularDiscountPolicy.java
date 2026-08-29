package com.example.order.domain;

public class RegularDiscountPolicy implements DiscountPolicy {

    private static final int DISCOUNT_THRESHOLD = 100_000;

    @Override
    public int calculateDiscount(int totalPrice) {
        if (totalPrice >= DISCOUNT_THRESHOLD) {
            return totalPrice / 10;
        }
        return 0;
    }
}
