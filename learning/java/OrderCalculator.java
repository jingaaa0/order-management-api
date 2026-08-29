public class OrderCalculator { // class: 관련 데이터와 동작을 묶는 Java의 기본 단위

    public static void main(String[] args) { // main: 프로그램이 시작되는 지점 / static: 객체를 생성하지 않고 메서드를 호출하기 위해 사용
        // 상품명, 가격, 수량 선언
        String productName = "키보드";
        System.out.println("상품명: " + productName);
        // 할인 전 금액 계산
        int originalPrice = 50000;
        System.out.println("가격: " + originalPrice);
        // 수량
        int count = 2;
        System.out.println("수량: " + count);
        // 할인 전 총 금액
        int originalTotalPrice = originalPrice * count;
        System.out.println("할인 전 금액: " + originalTotalPrice);
        // 할인 메서드 호출
        int discountAmount = calculateDiscount(originalTotalPrice);
        System.out.println("할인 금액: " + discountAmount);
        // 최종 금액
        int resultPrice = originalTotalPrice - discountAmount;
        System.out.println("최종 결제 금액: " + resultPrice);
    }

    static int calculateDiscount(int totalPrice) {
        // 100,000원 이상이면 10% 할인
        if (totalPrice >= 100000) {
            totalPrice = (int) (totalPrice * 0.1);
        // 그 외에는 할인 없음
        } else {
            totalPrice = 0;
        }

        return totalPrice;
    }
}