# Architecture

## 현재 구조

이 프로젝트는 Java 콘솔 주문 프로그램에서 Spring Boot REST API로 전환한 상태다.

```text
com.example.order
├── OrderApiApplication
├── controller
│   ├── OrderController
│   └── dto
│       └── CreateOrderRequest
├── domain
│   ├── DiscountPolicy
│   ├── InvalidOrderStateException
│   ├── Order
│   ├── OrderStatus
│   ├── RegularDiscountPolicy
│   └── VipDiscountPolicy
└── service
    └── OrderService
```

현재 요청 흐름은 다음과 같다.

```text
HTTP 요청
  -> OrderController
  -> OrderService
  -> Order
  -> OrderService의 메모리 목록
  -> OrderController
  -> JSON 응답
```

Day 8부터 저장 책임을 `Repository`로 분리해 다음 구조로 발전시킨다.

```text
Controller -> Service -> Repository
                  |
                Domain
```

## 클래스 책임

1. `OrderApiApplication`: Spring Boot 애플리케이션과 내장 웹 서버를 시작한다.
2. `OrderController`: 주문 HTTP 요청을 받고 Service를 호출한 뒤 HTTP 응답을 반환한다.
3. `CreateOrderRequest`: 주문 생성 JSON의 `productName`, `price`, `quantity`를 전달한다.
4. `Order`: 주문 한 건의 식별자, 값, 가격 계산 규칙, 상태 변경을 관리한다.
5. `OrderStatus`: 주문이 가질 수 있는 상태를 `CREATED`, `PAID`, `CANCELLED`로 제한한다.
6. `DiscountPolicy`: 할인 계산 구현체가 따라야 할 계약을 정의한다.
7. `RegularDiscountPolicy`: 총액이 100,000원 이상인 일반 주문의 10% 할인을 계산한다.
8. `VipDiscountPolicy`: VIP 주문의 20% 할인을 계산한다.
9. `InvalidOrderStateException`: 허용되지 않은 주문 상태 변경을 나타낸다.
10. `OrderService`: 주문을 생성하고 메모리에 저장하며 목록과 단건을 조회한다.

## Spring Boot 실행 과정

프로그램은 다음 코드에서 시작한다.

```java
SpringApplication.run(OrderApiApplication.class, args);
```

실행 순서는 다음과 같다.

1. Spring이 `ApplicationContext`라는 컨테이너를 생성한다.
2. `com.example.order`와 하위 패키지를 탐색한다.
3. `@Service`, `@RestController` 등이 붙은 클래스를 찾는다.
4. `OrderService` 객체를 Bean으로 생성한다.
5. `OrderController`를 만들면서 생성자에 `OrderService` Bean을 전달한다.
6. Spring MVC와 `DispatcherServlet`을 자동 설정한다.
7. 내장 Tomcat을 8080 포트에서 시작한다.
8. 애플리케이션이 HTTP 요청을 받을 수 있는 상태가 된다.

`OrderApiApplication`을 최상위 패키지인 `com.example.order`에 둔 이유는 `controller`, `service`, `domain` 패키지가 컴포넌트 스캔 범위에 포함되게 하기 위해서다.

## IoC와 Bean

### IoC

IoC는 Inversion of Control, 즉 제어의 역전이다.

일반 Java 코드에서는 애플리케이션이 직접 객체 생성 순서를 제어한다.

```java
OrderService orderService = new OrderService();
OrderController controller = new OrderController(orderService);
```

Spring에서는 컨테이너가 객체를 생성하고 연결한다.

```text
Spring 컨테이너
  -> OrderService 생성
  -> OrderController 생성
  -> OrderController 생성자에 OrderService 전달
```

객체 생성과 연결의 제어권이 애플리케이션 코드에서 Spring으로 이동했기 때문에 제어의 역전이라고 부른다.

### Bean

Bean은 Spring 컨테이너가 생성하고 관리하는 객체다.

현재 주요 Bean은 다음과 같다.

```text
orderService
orderController
```

`Order`는 요청마다 Service가 `new Order(...)`로 생성하는 도메인 객체이므로 현재 Spring Bean이 아니다.

`OrderService` Bean은 기본적으로 singleton이다. 애플리케이션 컨테이너 안에서 하나의 객체를 여러 요청이 공유한다. 따라서 현재 `orders` 목록도 모든 HTTP 요청이 공유한다.

## 의존성 주입

의존성 주입은 객체가 필요한 의존성을 직접 생성하지 않고 외부에서 전달받는 방식이다.

```java
private final OrderService orderService;

public OrderController(OrderService orderService) {
    this.orderService = orderService;
}
```

`OrderController`는 `new OrderService()`를 호출하지 않는다. Spring이 이미 생성한 `OrderService` Bean을 생성자에 전달한다.

생성자 주입을 사용하는 이유는 다음과 같다.

- 필요한 의존성이 생성자에 명확히 드러난다.
- 필드를 `final`로 선언할 수 있다.
- 불완전한 상태의 Controller가 생성되는 것을 막는다.
- 단위 테스트에서 Spring 없이 직접 가짜 객체나 실제 객체를 전달할 수 있다.

생성자가 하나뿐이면 Spring이 자동으로 선택하므로 `@Autowired`를 생략할 수 있다.

PHP의 DI 컨테이너가 생성자 타입을 보고 서비스를 주입하는 방식과 같은 개념이다.

## Spring 애노테이션

### `@SpringBootApplication`

Spring Boot 애플리케이션의 시작 설정을 나타낸다. 내부적으로 다음 세 역할을 묶어서 제공한다.

- `@Configuration`: 이 클래스가 Spring 설정 클래스임을 나타낸다.
- `@EnableAutoConfiguration`: 의존성과 설정을 보고 Spring MVC, Tomcat, JSON 변환기 등을 자동 설정한다.
- `@ComponentScan`: 현재 패키지와 하위 패키지에서 Spring 컴포넌트를 찾는다.

### `@Service`

비즈니스 유스케이스를 처리하는 클래스임을 나타낸다. 컴포넌트 스캔 대상이 되므로 Spring이 `OrderService`를 Bean으로 생성한다.

애노테이션이 계산 로직을 대신 실행하는 것은 아니다. 객체의 생성과 생명주기를 Spring이 관리하도록 표시하는 역할이다.

### `@RestController`

클래스가 HTTP 요청을 처리하는 REST Controller임을 나타낸다.

메서드가 반환한 `Order`나 `List<Order>`를 View 이름으로 해석하지 않고 HTTP 응답 본문에 JSON으로 작성한다.

### `@RequestMapping("/orders")`

Controller 전체에 적용되는 공통 URL을 지정한다.

```text
OrderController의 모든 API는 /orders로 시작한다.
```

### `@GetMapping`

HTTP GET 요청을 메서드에 연결한다.

```java
@GetMapping
```

위 코드는 `GET /orders`에 연결된다.

```java
@GetMapping("/{id}")
```

위 코드는 `GET /orders/{id}`에 연결된다.

### `@PostMapping`

HTTP POST 요청을 메서드에 연결한다.

```java
@PostMapping
```

클래스의 공통 경로와 합쳐져 `POST /orders`가 된다.

### `@RequestBody`

HTTP 요청 본문의 JSON을 Java 객체로 변환한다.

```json
{
  "productName": "키보드",
  "price": 50000,
  "quantity": 2
}
```

위 JSON은 `CreateOrderRequest` record로 변환된다.

```java
request.productName()
request.price()
request.quantity()
```

### `@PathVariable("id")`

URL 경로의 일부를 메서드 매개변수로 전달한다.

```text
GET /orders/550e8400-e29b-41d4-a716-446655440000
            ------------------------------------
                              id
```

Spring은 경로의 문자열을 `UUID` 객체로 변환해 Controller에 전달한다.

### `@ResponseStatus(HttpStatus.CREATED)`

메서드가 정상 반환될 때 HTTP 상태를 `201 Created`로 지정한다.

주문 생성은 새로운 리소스를 만들기 때문에 일반적인 `200 OK`보다 `201 Created`가 의미에 맞다.

### `@SpringBootTest`

테스트에서 실제 Spring `ApplicationContext`를 생성한다.

`contextLoads()` 메서드가 비어 있어도 다음 과정 중 하나가 실패하면 테스트가 실패한다.

- 컴포넌트 스캔
- Bean 생성
- 생성자 의존성 주입
- Spring Boot 자동 설정

## HTTP API 흐름

### 주문 생성

```text
POST /orders
  -> DispatcherServlet이 요청 수신
  -> OrderController.createOrder()
  -> JSON을 CreateOrderRequest로 변환
  -> OrderService.createOrder()
  -> Order 생성
  -> Service의 orders 목록에 저장
  -> Order 반환
  -> Order를 JSON으로 변환
  -> 201 Created
```

### 주문 목록 조회

```text
GET /orders
  -> OrderController.getOrders()
  -> OrderService.findAll()
  -> 내부 목록을 List.copyOf()로 복사
  -> 주문 배열을 JSON으로 변환
  -> 200 OK
```

`List.copyOf()`를 사용하므로 Controller가 Service 내부 목록에 주문을 직접 추가하거나 삭제할 수 없다.

### 주문 단건 조회

```text
GET /orders/{id}
  -> URL 문자열을 UUID로 변환
  -> OrderController.getOrder()
  -> OrderService.findById()
  -> 주문이 있으면 200 OK와 주문 JSON
  -> 주문이 없으면 404 Not Found
```

## JSON 변환

Spring Boot의 JSON 변환기가 Java 객체와 JSON 사이를 변환한다.

```text
요청 JSON -> CreateOrderRequest
Order -> 응답 JSON
List<Order> -> JSON 배열
```

`CreateOrderRequest`를 record로 만든 이유는 요청 데이터를 전달하는 불변 DTO에 적합하기 때문이다.

`Order`의 응답 JSON은 public getter를 기준으로 만들어진다.

```text
getId()          -> id
getProductName() -> productName
getPrice()       -> price
getQuantity()    -> quantity
getStatus()      -> status
```

## HTTP 상태 코드

| 상황 | 상태 코드 | 의미 |
|---|---:|---|
| 주문 생성 성공 | `201` | 새로운 주문 리소스 생성 |
| 목록 조회 성공 | `200` | 요청 정상 처리 |
| 단건 조회 성공 | `200` | 주문 조회 성공 |
| 주문 없음 | `404` | 해당 ID의 주문이 없음 |
| 잘못된 JSON | `400` | 요청 본문을 DTO로 변환할 수 없음 |

## 테스트 구분

### 순수 단위 테스트

`OrderTest`, `OrderStatusTest`, `OrderServiceTest`는 Spring을 시작하지 않는다.

```java
orderService = new OrderService();
```

장점은 실행이 빠르고 실패 원인을 작은 코드 단위로 찾기 쉽다는 것이다.

### Spring 컨텍스트 테스트

`OrderApiApplicationTest`는 `@SpringBootTest`를 사용해 Spring을 시작한다.

이 테스트는 개별 계산 결과보다 Spring 설정과 Bean 연결이 정상인지 검증한다.

## 현재 한계와 다음 단계

현재 구조는 Spring MVC 학습을 위한 임시 메모리 구현이다.

- 서버를 재시작하면 주문이 모두 사라진다.
- `OrderService`가 유스케이스와 저장 책임을 함께 가진다.
- `ArrayList`는 여러 요청이 동시에 수정할 때 안전하지 않다.
- Controller가 도메인 객체 `Order`를 응답에 직접 노출한다.
- 요청값 검증과 일관된 예외 응답이 아직 없다.

Day 8에서는 다음 구조로 개선한다.

```text
Controller: HTTP 요청, 검증, 응답 DTO
Service: 주문 유스케이스
Repository: 주문 저장과 조회
Domain: 주문 상태와 비즈니스 규칙
```
