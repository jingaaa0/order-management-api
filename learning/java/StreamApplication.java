import java.util.List;
import java.util.Comparator;

public class StreamApplication {
    public static void main(String[] args) {

        List<Order> orders = List.of(
                new Order("키보드", 50_000, 2),
                new Order("마우스", 30_000, 1),
                new Order("모니터", 200_000, 1)
        );

        // 합산
        int totalPaymentAmount = orders.stream()
            .mapToInt(Order::calculateFinalPrice) // 최종결제금액을 int로 변환
            .sum();                               // 모든 int 합산

        System.out.println("전체 결제 금액: " + totalPaymentAmount + "원");

        // 축소
        int totalByReduce = orders.stream()
            .map(Order::calculateFinalPrice)
            // .reduce(0, (subtotal, finalprice) -> subtotal + finalprice);
            .reduce(0, Integer::sum);

        System.out.println(totalByReduce + "원");

        // // 정렬
        // List<Order> sortedOrders = orders.stream()
        //     .sorted(
        //         // Comparator: 두 객체의 정렬 순서를 결정
        //         // comparingInt(...): 각 Order에서 정렬 기준이 될 int 값을 추출
        //         // Order::calculateFinalPrice: 최종 결제 금액을 정렬 기준으로 사용
        //         // reversed(): 기본 오름차순을 내림차순으로 반전
        //         Comparator.comparingInt(Order::calculateFinalPrice)
        //             .reversed()
        //     )
        //     .toList();

        // sortedOrders.forEach(order -> {
        //     System.out.println(order.getProductName() + ": " + order.calculateFinalPrice() + "원");
        // });

        // // 반환
        // List<String> productNames = orders.stream()
        //         .map(order -> order.getProductName())
        //         .toList();

        // productNames.forEach(product -> {
        //     System.out.println("상품명: " + product);
        // });

        // // 필터링
        // List<Order> expensiveOrders = orders.stream()
        //     .filter(order -> order.calculateFinalPrice() >= 100_000)
        //     .toList();

        // System.out.println("필터 전 주문 수: " + orders.size());
        // expensiveOrders.forEach(order -> {
        //     System.out.println(order.getProductName() + ": " + order.calculateFinalPrice() + "원");
        // });
        // System.out.println("필터 후 주문 수: " + expensiveOrders.size());


    }
}