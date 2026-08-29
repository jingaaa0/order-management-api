import java.util.ArrayList;
import java.util.List;

public class GenericApplication {

    public static void main(String[] args) {
        List<Order> orders = new ArrayList<>();

        orders.add(new Order("키보드", 50_000, 2));
        // orders.add("키보드");
        // orders.add(50_000);

        // 1번: 성공
        // 이유: new Order(...)로 생성한 Order 객체를 List<Order>에 추가했기 때문

        // 2번: 컴파일 오류
        // 이유: List<Order>에는 String 객체를 추가할 수 없기 때문

        // 3번: 컴파일 오류
        // 이유: List<Order>에는 Integer 값을 추가할 수 없기 때문

        // List → 위치와 순서가 있으며 중복 허용
        // Map  → key와 value가 있으며 key 중복 불가
        // Set  → 값만 저장하며 중복 불가
    }

}