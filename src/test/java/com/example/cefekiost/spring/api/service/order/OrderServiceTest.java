package com.example.cefekiost.spring.api.service.order;

import com.example.cefekiost.spring.api.service.order.request.OrderCreateRequest;
import com.example.cefekiost.spring.api.service.order.response.OrderResponse;
import com.example.cefekiost.spring.domain.order.OrderRepository;
import com.example.cefekiost.spring.domain.orderProduct.OrderProductRepository;
import com.example.cefekiost.spring.domain.product.Product;
import com.example.cefekiost.spring.domain.product.ProductRepository;
import com.example.cefekiost.spring.domain.product.ProductType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.cefekiost.spring.domain.product.ProductSellingStatus.*;
import static com.example.cefekiost.spring.domain.product.ProductType.HANDMADE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest // 이놈은 트랜잭션이 안걸려 있고, @DataJpaTest에는 트랜잭션이 걸려있음
@ActiveProfiles("test")
@Transactional // 이렇게 추가 할 경우 문제점이 뭘까??
class OrderServiceTest {

    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderProductRepository orderProductRepository;

    @AfterEach
    void tearDown() {
        orderProductRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
        orderRepository.deleteAllInBatch();

        // TODO deleteAll() 과 차이가 멀까.
        // 트랜잭션쓰면 되지 않음??

    }

    @Test
    @DisplayName("주문번호 리스트를 받아 주문을 생성한다.")
    public void createOrder() throws Exception{
        //given

        Product product1 = createProduct(HANDMADE, "001", 1000);
        Product product2 = createProduct(HANDMADE, "002", 3000);
        Product product3 = createProduct(HANDMADE, "003", 5000);
        productRepository.saveAll(List.of(product1, product2, product3));


        OrderCreateRequest request = OrderCreateRequest.builder()
                .productNumbers(List.of("001", "002"))
                .build();

        LocalDateTime orderRegisteredDateTime = LocalDateTime.now();
        //when
        OrderResponse orderResponse = orderService.createOrder(request,orderRegisteredDateTime);

        //then
        // red -> green -
        assertThat(orderResponse.getId()).isNotNull();
        assertThat(orderResponse)
                .extracting("registeredDateTime", "totalPrice")
                .contains(orderRegisteredDateTime, 4000);
        assertThat(orderResponse.getProducts()).hasSize(2)
                .extracting("productNumber", "price")
                .containsExactlyInAnyOrder(
                        tuple("001",1000),
                        tuple("002",3000)

                );
    }

    @Test
    @DisplayName("동일한 상품(중복)을 주문할 수 있다.")
    public void createOrderWithDuplicateProductNumbers() throws Exception{
        //given

        Product product1 = createProduct(HANDMADE, "001", 1000);
        Product product2 = createProduct(HANDMADE, "002", 3000);
        Product product3 = createProduct(HANDMADE, "003", 5000);
        productRepository.saveAll(List.of(product1, product2, product3));


        OrderCreateRequest request = OrderCreateRequest.builder()
                .productNumbers(List.of("001", "001"))
                .build();

        LocalDateTime orderRegisteredDateTime = LocalDateTime.now();
        //when
        OrderResponse orderResponse = orderService.createOrder(request,orderRegisteredDateTime);

        //then
        // red -> green -
        assertThat(orderResponse.getId()).isNotNull();
        assertThat(orderResponse)
                .extracting("registeredDateTime", "totalPrice")
                .contains(orderRegisteredDateTime, 2000);
        assertThat(orderResponse.getProducts()).hasSize(2)
                .extracting("productNumber", "price")
                .containsExactlyInAnyOrder(
                        tuple("001",1000),
                        tuple("001",1000)

                );
    }

    private Product createProduct(ProductType type, String productNumber, int price){
        return Product.builder()
                .productType(type)
                .productNumber(productNumber)
                .price(price)
                .productSellingStatus(SELLING)
                .name("메뉴 이름")
                .build();
    }
}