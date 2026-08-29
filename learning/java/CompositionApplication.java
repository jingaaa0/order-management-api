public class CompositionApplication {
    public static void main(String[] args) {
        Order regularOrder = new Order("키보드", 50_000, 2, new RegularDiscountPolicy());
        Order vipOrder = new Order("키보드", 50_000, 2, new VipDiscountPolicy());

        System.out.println("일반 주문 결제 금액: " + regularOrder.calculateFinalPrice() + "원");
        System.out.println("VIP 주문 결제 금액: " + vipOrder.calculateFinalPrice() + "원");
    }
}