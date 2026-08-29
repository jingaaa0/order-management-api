import java.util.List;

public class LambdaApplication {
    public static void main(String[] args) {
        List<Order> orders = List.of(
                new Order("키보드", 50_000, 2),
                new Order("마우스", 30_000, 1),
                new Order("모니터", 200_000, 1)
        );

        orders.forEach(order -> {
            System.out.println(order.getProductName() + ": " + order.calculateFinalPrice() + "원");
        });
    }
}