import java.util.HashSet;
import java.util.Set;

public class OrderSetApplication {
    public static void main(String[] args) {

        Set<String> productNames = new HashSet<>();
        boolean added = productNames.add("키보드");
        System.out.println("첫 번째 키보드 추가: " + added);
        productNames.add("마우스");
        productNames.add("모니터");
        added = productNames.add("키보드");
        System.out.println("두 번째 키보드 추가: " + added);
        System.out.println("상품 종류 수: " + productNames.size());
        System.out.println("마우스 존재 여부: " + productNames.contains("마우스"));

        for (String productName : productNames) {
            System.out.println("상품명: " + productName);
        }

    }
}