package com.example.cefekiost.spring.domain.order;

import com.example.cefekiost.spring.domain.product.Product;
import com.example.cefekiost.spring.domain.product.ProductType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.cefekiost.spring.domain.product.ProductSellingStatus.SELLING;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

// 단위 테스트라 별다른 어노테이션 할필요없음
class OrderTest {

    @Test
    @DisplayName("주문 생성 시 상품 리스트에서 주문의 총 금액을 계산한다.")
    public void calculateTotalPrice() throws Exception {
        //given
        List<Product> products = List.of(
                createProduct("001", 1000),
                createProduct("002", 2000)
        );

        //when
        LocalDateTime orderedDateTime = LocalDateTime.now();
        Order order = Order.create(products, orderedDateTime);

        //then
        assertThat(order.getTotalPrice()).isEqualTo(3000);
        assertThat(order.getRegisteredDateTime()).isEqualTo(orderedDateTime);
    }

    @Test
    @DisplayName("주문 생성 시 주문 상태는 INIT이다.")
    public void init() throws Exception {
        //given
        List<Product> products = List.of(
                createProduct("001", 1000),
                createProduct("002", 2000)
        );

        //when
        LocalDateTime orderedDateTime = LocalDateTime.now();
        Order order = Order.create(products, orderedDateTime);

        //then
        assertThat(order.getOrderStatus()).isEqualByComparingTo(OrderStatus.INIT);
    }

    private Product createProduct( String productNumber, int price){
        return Product.builder()
                .productNumber(productNumber)
                .price(price)
                .productSellingStatus(SELLING)
                .name("메뉴 이름")
                .build();
    }

}