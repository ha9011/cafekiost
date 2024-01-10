package sample.cafekiosk.spring.api.service.order.response;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import sample.cafekiosk.spring.api.service.product.response.ProductReponse;
import sample.cafekiosk.spring.domain.order.Order;
import sample.cafekiosk.spring.domain.order.OrderStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@ToString
@NoArgsConstructor
public class OrderResponse {
    private long id;

    private int totalPrice;

    private LocalDateTime registeredDateTime;

    private List<ProductReponse> products = new ArrayList<>();

    @Builder
    public OrderResponse(long id, int totalPrice, LocalDateTime registeredDateTime, List<ProductReponse> products) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.registeredDateTime = registeredDateTime;
        this.products = products;
    }

    public static OrderResponse of(Order order){
        return OrderResponse.builder()
                .id(order.getId())
                .totalPrice(order.getTotalPrice())
                .registeredDateTime(order.getRegisteredDataTime())
                .products(order.getOrderProduct().stream().map(orderProduct -> ProductReponse.of(orderProduct.getProduct())).collect(Collectors.toList()))
                .build();


    }

}
