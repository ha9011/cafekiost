package com.example.cefekiost.spring.api.service.order.response;

import com.example.cefekiost.spring.api.service.product.response.ProductResponse;
import com.example.cefekiost.spring.domain.order.OrderStatus;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class OrderResponse {
    private Long id;
    private int totalPrice;
    private LocalDateTime registeredDateTime;
    private List<ProductResponse> products;

}
