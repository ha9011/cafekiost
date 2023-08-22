package com.example.cefekiost.spring.api.service.order;


import com.example.cefekiost.spring.api.service.order.request.OrderCreateRequest;
import com.example.cefekiost.spring.api.service.order.response.OrderResponse;
import com.example.cefekiost.spring.domain.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final ProductRepository productRepository;
    public OrderResponse createOrder(OrderCreateRequest request) {
        List<String> productNumbers = request.getProductNumbers();
        // product

        // 이놈도 테스트
        productRepository.findAllByProductNumberIn(productNumbers);

        //
        return null;
    }
}
