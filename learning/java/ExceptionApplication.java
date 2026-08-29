public class ExceptionApplication {

    public static void main(String[] args) {
        try {
            // 잘못된 수량으로 Order 생성
            DiscountPolicy regularPolicy = new RegularDiscountPolicy();
            Order order = new Order("마우스", 50_000, 0, regularPolicy);
        } catch (IllegalArgumentException exception) {
            // exception.getMessage() 출력
            System.out.println(exception.getMessage());
        }
    }
}