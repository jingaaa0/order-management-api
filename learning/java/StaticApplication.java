public class StaticApplication {
    public static void main(String[] args) {

        System.out.println("생성 전 주문 수: " + Order.getCreatedOrderCount());
        
        Order keyboardOrder = new Order("키보드", 50_000, 2);
        Order mouseOrder = new Order("마우스", 30_000, 1);
        Order monitorOrder = new Order("모니터", 200_000, 1);
        
        System.out.println("생성된 주문 수: " + Order.getCreatedOrderCount());

    }
}