public class OrderStateExceptionApplication {

    public static void main(String[] args) {
        try {
            Order order = new Order("키보드", 50_000, 2);

            order.cancel();
            order.pay();
        } catch (InvalidOrderStateException exception) {
            System.out.println(exception.getMessage());
        }
    }
}