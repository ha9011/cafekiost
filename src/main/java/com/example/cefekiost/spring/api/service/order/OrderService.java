package com.example.cefekiost.spring.api.service.order;


import com.example.cefekiost.spring.api.service.order.request.OrderCreateRequest;
import com.example.cefekiost.spring.api.service.order.response.OrderResponse;
import com.example.cefekiost.spring.domain.order.Order;
import com.example.cefekiost.spring.domain.order.OrderRepository;
import com.example.cefekiost.spring.domain.product.Product;
import com.example.cefekiost.spring.domain.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    public OrderResponse createOrder(OrderCreateRequest request, LocalDateTime registeredDateTime) {
        List<String> productNumbers = request.getProductNumbers();
        System.out.println("productNumbers : " + productNumbers);
        // product
        // 이놈도 테스트 test! (1)
        List<Product> duplicatieProducts = findProductBy(productNumbers);

        Order order = Order.create(duplicatieProducts, registeredDateTime);
        Order savedOrder = orderRepository.save(order);

        return OrderResponse.of(savedOrder);
    }

    private List<Product> findProductBy(List<String> productNumbers) {
        List<Product> products = productRepository.findAllByProductNumberIn(productNumbers);
        System.out.println("products : " + products);
        Map<String, Product> productMap = products.stream().collect(Collectors.toMap(p -> p.getProductNumber(), product -> product));
        //System.out.println("productMap : " + productMap.keySet());

        List<Product> duplicatieProducts = productNumbers.stream().map(pn -> productMap.get(pn))
                .collect(Collectors.toList());
        return duplicatieProducts;
    }
}
