import java.io.IOException;     // 파일이 없거나 읽을 수 없을 때 발생하는 checked exception
import java.nio.file.Files;     // 파일 전체 내용을 String으로 읽음
import java.nio.file.Path;      // 파일 경로를 표현하는 Path 객체 생성

public class OrderFileApplication {
    public static void main(String[] args) {
        try {
            String content = readOrderFile("orders.txt");
            System.out.println(content);
        } catch (IOException exception) {
            System.out.println("파일을 읽을 수 없습니다.");
            System.out.println("원인: " + exception.getMessage());
        } finally {
            System.out.println("파일 읽기 시도가 끝났습니다.");
        }

    } 

    // throw  → 실제 예외 객체를 발생시킴
    // throws → 이 메서드에서 예외가 밖으로 전달될 수 있다고 선언
    static String readOrderFile(String fileName) throws IOException {
        return Files.readString(Path.of(fileName));
    }
}