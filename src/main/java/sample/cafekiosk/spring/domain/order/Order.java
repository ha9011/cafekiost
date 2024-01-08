package sample.cafekiosk.spring.domain.order;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.aspectj.weaver.ast.Or;
import sample.cafekiosk.spring.domain.BaseEntity;
import sample.cafekiosk.spring.domain.orderproduct.OrderProduct;
import sample.cafekiosk.spring.domain.product.Product;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private int totalPrice;

    private LocalDateTime registeredDataTime;

    // mappedBy = "order" : 상대쪽 필드, 연관관계 주인 설정
    // cascade = CascadeType.ALL : orderProduct가 삭제, 변경되면 영향받음
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderProduct> orderProduct = new ArrayList<>();



    public Order(List<Product> products, LocalDateTime now) {
        this.orderStatus = OrderStatus.INIT;
        this.totalPrice = this.calculateTotalPrice(products);
        this.registeredDataTime = now;
        this.orderProduct = products.stream()
                .map(product -> new OrderProduct(this, product))
                .collect(Collectors.toList());

    }

    public static Order create(List<Product> products, LocalDateTime registeredDateTime) {
        return new Order(products, registeredDateTime);
    }


//    @Builder
//    public Order(OrderStatus orderStatus, int totalPrice, LocalDateTime registeredDataTime, List<OrderProduct> orderProduct) {
//        this.orderStatus = orderStatus;
//        this.totalPrice = totalPrice;
//        this.registeredDataTime = registeredDataTime;
//        this.orderProduct = orderProduct;
//    }
//
//    public static Order create(List<Product> products, LocalDateTime registeredDateTime) {
//        OrderStatus initStatus = OrderStatus.INIT;
//        int totalPrice = new Order().calculateTotalPrice(products); // static(정적)메소드 안에서, 비정적메소드를 이용하려면, 초기화 하고 써야한다.
//
//        List<OrderProduct> collect = products.stream()
//                .map(product -> new OrderProduct(this, product))
//                .collect(Collectors.toList());
//
//        return Order.builder()
//                .orderStatus(initStatus)
//                .totalPrice(totalPrice)
//                .orderProduct(collect)
//                .registeredDataTime(registeredDateTime)
//                .build();
//    }

    private int calculateTotalPrice(List<Product> products) {
        return products.stream().mapToInt(Product::getPrice).sum();
    }


}
