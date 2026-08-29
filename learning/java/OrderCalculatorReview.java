public class OrderCalculatorReview {

    public static void main(String[] args) {
        String productName = "키보드";
        int price = 50_000;
        int quantity = 2;

        int totalPrice = price * quantity;
        int discountAmount = calculateDiscount(totalPrice);
        int finalPrice = totalPrice - discountAmount;

        System.out.println("상품명: " + productName);
        System.out.println("가격: " + price);
        System.out.println("수량: " + quantity);
        System.out.println("할인 전 금액: " + totalPrice);
        System.out.println("할인 금액: " + discountAmount);
        System.out.println("최종 결제 금액: " + finalPrice);
    }

    static int calculateDiscount(int totalPrice) {
        if (totalPrice >= 100_000) {
            return totalPrice / 10;
        }

        return 0;
    }
}
