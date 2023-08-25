package com.example.cefekiost.spring.api.service.order;


import com.example.cefekiost.spring.api.service.order.request.OrderCreateRequest;
import com.example.cefekiost.spring.api.service.order.response.OrderResponse;
import com.example.cefekiost.spring.domain.order.Order;
import com.example.cefekiost.spring.domain.order.OrderRepository;
import com.example.cefekiost.spring.domain.product.Product;
import com.example.cefekiost.spring.domain.product.ProductRepository;
import com.example.cefekiost.spring.domain.product.ProductType;
import com.example.cefekiost.spring.domain.stock.Stock;
import com.example.cefekiost.spring.domain.stock.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final StockRepository stockRepository;
    public OrderResponse createOrder(OrderCreateRequest request, LocalDateTime registeredDateTime) {
        List<String> productNumbers = request.getProductNumbers();
        System.out.println("productNumbers : " + productNumbers);
        // product
        // 이놈도 테스트 test! (1)
        List<Product> products = findProductBy(productNumbers);

        deductStockQuantities(products);


        Order order = Order.create(products, registeredDateTime);
        Order savedOrder = orderRepository.save(order);

        return OrderResponse.of(savedOrder);
    }

    private void deductStockQuantities(List<Product> products) {
        // 재고차감 체크가 필요한 상품들 filter
        List<String> stockProductNumbers = extractStockProductNumbers(products);
        // 재고 엔티티 조회
        Map<String, Stock> stockMap = createStockMapBy(stockProductNumbers);

        // 상품별 counting
        Map<String, Long> productCountMap = createCountMapBy(stockProductNumbers);

        // 재고 차감 시도
        for(String stockProductNumber : productCountMap.keySet()){
            int count = productCountMap.get(stockProductNumber).intValue();
            Stock stock = stockMap.get(stockProductNumber);

            // deductQuantity에도 재고 체크가 있지만 굳이 추가하는 이유
            // 1. Stock 엔티티 만의 에러체크가 존재하면 좋음
            // 2. 서비스 단에서 에러 메시지를 유저에게 다르게 보내고 싶을 경우
            if(stock.isQuantityLessThan(count)){
                throw new IllegalArgumentException("재고가 부족한 상품이 있습니다.");
            }
            stock.deductQuantity(count);

        }
    }

    private static List<String> extractStockProductNumbers(List<Product> products) {
        List<String> stockProductNumbers = products.stream()
                .filter(product -> ProductType.containsStockType(product.getType()))
                .map(Product::getProductNumber)
                .collect(Collectors.toList());
        return stockProductNumbers;
    }

    private Map<String, Stock> createStockMapBy(List<String> stockProductNumbers) {
        List<Stock> stocks = stockRepository.findAllByProductNumberIn(stockProductNumbers);
        Map<String, Stock> stockMap = stocks.stream()
                .collect(Collectors.toMap(Stock::getProductNumber, s -> s));
        return stockMap;
    }

    private static Map<String, Long> createCountMapBy(List<String> stockProductNumbers) {
        Map<String, Long> productCountMap = stockProductNumbers.stream()
                .collect(Collectors.groupingBy(p -> p, Collectors.counting()));
        return productCountMap;
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
