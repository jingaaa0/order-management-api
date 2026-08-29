public class DiscountPolicyApplication {

    public static void main(String[] args) {
        DiscountPolicy regularPolicy = new RegularDiscountPolicy();
        DiscountPolicy vipPolicy = new VipDiscountPolicy();

        // 두 정책의 할인 금액 확인
        System.out.println("80,000원 - 일반 할인: " + regularPolicy.calculateDiscount(80_000) + "원");
        System.out.println("150,000원 - 일반 할인: " + regularPolicy.calculateDiscount(150_000) + "원");
        System.out.println("80,000원 - VIP 할인: " + vipPolicy.calculateDiscount(80_000) + "원");
        System.out.println("150,000원 -VIP 할인: " + vipPolicy.calculateDiscount(150_000) + "원");
    }
}