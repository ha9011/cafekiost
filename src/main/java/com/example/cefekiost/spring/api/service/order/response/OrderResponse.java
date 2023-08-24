package com.example.cefekiost.spring.api.service.order.response;

import com.example.cefekiost.spring.api.service.product.response.ProductResponse;
import com.example.cefekiost.spring.domain.order.Order;
import com.example.cefekiost.spring.domain.order.OrderStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class OrderResponse {
    private Long id;
    private int totalPrice;
    private LocalDateTime registeredDateTime;
    private List<ProductResponse> products;

    @Builder
    public OrderResponse(Long id, int totalPrice, LocalDateTime registeredDateTime, List<ProductResponse> products) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.registeredDateTime = registeredDateTime;
        this.products = products;
    }

    public static OrderResponse of(Order order){
        return OrderResponse.builder()
                .id(order.getId())
                .products(
                        order.getOrderProducts().stream()
                                .map(orderProduct -> ProductResponse.of(orderProduct.getProduct())
                        ).collect(Collectors.toList()))
                .registeredDateTime(order.getRegisteredDateTime())
                .totalPrice(order.getTotalPrice())
                .build();

    }
}
