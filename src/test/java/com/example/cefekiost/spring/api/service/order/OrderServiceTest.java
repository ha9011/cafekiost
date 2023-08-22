package com.example.cefekiost.spring.api.service.order;

import com.example.cefekiost.spring.api.service.order.request.OrderCreateRequest;
import com.example.cefekiost.spring.api.service.order.response.OrderResponse;
import com.example.cefekiost.spring.domain.product.Product;
import com.example.cefekiost.spring.domain.product.ProductRepository;
import com.example.cefekiost.spring.domain.product.ProductType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.cefekiost.spring.domain.product.ProductSellingStatus.*;
import static com.example.cefekiost.spring.domain.product.ProductType.HANDMADE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderServiceTest {

    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductRepository productRepository;

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

        //when
        OrderResponse orderResponse = orderService.createOrder(request);

        //then
        // red -> green -
        assertThat(orderResponse.getId()).isNotNull();
        assertThat(orderResponse)
                .extracting("registeredDateTime", "totalPrice")
                .contains(LocalDateTime.now(), 4000);
        assertThat(orderResponse.getProducts()).hasSize(2)
                .extracting("productNumber", "price")
                .containsExactlyInAnyOrder(
                        tuple("001",1000),
                        tuple("002",3000)

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