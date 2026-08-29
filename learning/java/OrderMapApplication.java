import java.util.HashMap;
import java.util.Map;

public class OrderMapApplication {

    public static void main(String[] args) {
        Map<String, Order> orderMap = new HashMap<>();
        // String -> key, Order -> value

        Order keyboardOrder = new Order("키보드", 50_000, 2);
        orderMap.put(keyboardOrder.getProductName(),keyboardOrder);

        Order mouseOrder = new Order("마우스", 30_000, 1);
        orderMap.put(mouseOrder.getProductName(), mouseOrder);

        Order monitorOrder = new Order("모니터", 200_000, 1);
        orderMap.put(monitorOrder.getProductName(), monitorOrder);

        // System.out.println("마우스 결제 금액: " + orderMap.get("마우스").calculateFinalPrice() + "원");
        // System.out.println("전체 주문 수: " + orderMap.size());


        int totalPaymentAmount = 0;
        for (Order order : orderMap.values()) {
            int finalPrice = order.calculateFinalPrice();
            totalPaymentAmount += finalPrice;
        }

        // System.out.println("전체 결제 금액: " + totalPaymentAmount + "원");


        String searchProductName = "마우스";
        Order foundOrder = orderMap.get(searchProductName);
        System.out.println("검색 상품: " + searchProductName);
        if (foundOrder == null) {
            // 주문을 찾을 수 없다고 출력
            System.out.println("주문을 찾을 수 없습니다.");
        } else {
            // 최종 결제 금액 출력
            System.out.println("결제 금액: " + foundOrder.calculateFinalPrice() + "원");
        }

        System.out.println("================================================");


        for (Map.Entry<String, Order> entry : orderMap.entrySet()) {
            String productName = entry.getKey();
            Order order = entry.getValue();

            // 상품명과 최종 결제 금액 출력
            System.out.println("상품명: " + productName);
            System.out.println("최종 결제 금액: " + order.calculateFinalPrice() + "원");
        }


        String replaceProduct = "키보드";
        Order replacementOrder = new Order(replaceProduct, 60_000, 1);
        // Order previousOrder = orderMap.put(replaceProduct, replacementOrder);
        // System.out.println("교체 전 주문 수: " + previousOrder.size());
        // System.out.println("이전 " + replaceProduct + " 결제 금액: " + previousOrder.calculateFinalPrice() + "원");
        // int replaceProductAfterAmount = 0;
        // int replaceProductAfterQuantity = 0;
        // Order getReplaceProduct = orderMap.get(replaceProduct);
        // if (getReplaceProduct != null) {
        //     replaceProductAfterAmount = getReplaceProduct.calculateFinalPrice();
        //     replaceProductAfterQuantity = getReplaceProduct.getQuantity();
        // }
        // System.out.println("교체 후 " + replaceProduct + " 결제 금액: " + replaceProductAfterAmount + "원");
        // System.out.println("교체 후 주문 수: " + replaceProductAfterQuantity);

        String deleteProduct = "마우스";
        // Order removedOrder = orderMap.remove(deleteProduct);
        // System.out.println("삭제된 상품: " + deleteProduct);
        // System.out.println("삭제된 주문 결제 금액: " + removedOrder.calculateFinalPrice());
        // int getQuantity = 0;
        // Order getDeleteProduct = orderMap.get(deleteProduct);
        // if (getDeleteProduct != null) {
        //     getQuantity = getDeleteProduct.getQuantity();
        // } 
        // System.out.println("삭제 후 주문 수: " + getQuantity);


        // order.getQuantity() -> 해당 주문에 포함된 상품 개수
        // orderMap.size() -> Map에 저장된 주문 개수
        System.out.println("교체 전 주문 수: " + orderMap.size());

        Order previousOrder = orderMap.put(replaceProduct, replacementOrder);

        System.out.println("이전 결제 금액: " + previousOrder.calculateFinalPrice() + "원");
        System.out.println("교체 후 결제 금액: " + replacementOrder.calculateFinalPrice() + "원");
        System.out.println("교체 후 주문 수: " + orderMap.size());

        Order removedOrder = orderMap.remove(deleteProduct);

        System.out.println("삭제된 결제 금액: " + removedOrder.calculateFinalPrice() + "원");
        System.out.println("삭제 후 주문 수: " + orderMap.size());

    }

}