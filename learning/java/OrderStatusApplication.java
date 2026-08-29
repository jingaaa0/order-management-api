public class OrderStatusApplication {
    public static void main(String[] args) {
        // Order 생성
        // -> 현재 상태 출력
        Order order = new Order("마우스", 50_000, 2);
        System.out.println(order.getStatus());
        // -> pay() 호출
        order.pay();
        // -> 변경된 상태 출력
        System.out.println(order.getStatus());
        // -> cancel() 호출
        order.cancel();
        // -> 변경된 상태 출력
        System.out.println(order.getStatus());
    }
}