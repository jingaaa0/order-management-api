package com.example.order.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderTest {

    @Test
    void appliesTenPercentDiscountAtThreshold() {
        Order order = new Order("키보드", 50_000, 2);
        int discountAmount = order.calculateDiscount();
        assertThat(discountAmount).isEqualTo(10_000);
    }

    @Test
    void doesNotApplyDiscountBelowThreshold() {
        Order order = new Order("키보드", 99_999, 1);
        int discountAmount = order.calculateDiscount();
        assertThat(discountAmount).isEqualTo(0);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, -100})
    void rejectsNonPositivePrice(int invalidPrice) {
        assertThatThrownBy(() -> new Order("키보드", invalidPrice, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("가격은 0보다 커야 합니다.");
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, -10})
    void rejectsNonPositiveQuantity(int invalidQuantity) {
        assertThatThrownBy(() -> new Order("키보드", 50_000, invalidQuantity))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("수량은 1 이상이어야 합니다.");
    }

    @Test
    void calculatesFinalPriceAfterDiscount() {
        Order order = new Order("키보드", 50_000, 2);
        int finalAmount = order.calculateFinalPrice();
        assertThat(finalAmount).isEqualTo(90_000);
    }

    @Test
    void rejectsPaymentForCancelledOrder() {
        Order order = new Order("키보드", 50_000, 2);
        order.cancel();
        assertThatThrownBy(() -> order.pay())
                .isInstanceOf(InvalidOrderStateException.class)
                .hasMessage("CREATED 상태의 주문만 결제할 수 있습니다. 현재 상태: CANCELLED");
    }

    @Test
    void appliesTwentyPercentDiscountForVip() {
        Order vipOrder = new Order("키보드", 50_000, 2, new VipDiscountPolicy());
        int discountAmount = vipOrder.calculateDiscount();
        assertThat(discountAmount).isEqualTo(20_000);
    }
}
