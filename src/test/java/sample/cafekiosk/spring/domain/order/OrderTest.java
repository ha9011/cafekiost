package sample.cafekiosk.spring.domain.order;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;
import sample.cafekiosk.spring.domain.product.ProductType;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


class OrderTest {
    @Test
    @DisplayName("주문 생성 시,상품리스트에서 주문의 총 금액을 계산한다.")
    void calculateTotalPrice() {
        // given
        Product product1 = this.createProduct("001", 2000);
        Product product2 = this.createProduct("002", 4000);
        List<Product> products = List.of(
                product1, product2
        );

        LocalDateTime registeredDateTime = LocalDateTime.now();
        // when
        Order order = Order.create(products, registeredDateTime);

        // then
        assertThat(order.getTotalPrice()).isEqualTo(3000);

    }

    @Test
    @DisplayName("주문 생성 시, 주문의 상태값은 INIT 이다.")
    void createOrderStatus() {
        // given
        Product product1 = this.createProduct("001", 2000);
        Product product2 = this.createProduct("002", 4000);
        List<Product> products = List.of(
                product1, product2
        );

        LocalDateTime registeredDateTime = LocalDateTime.now();
        // when
        Order order = Order.create(products, registeredDateTime);

        // then
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.INIT);

    }

    @Test
    @DisplayName("주문 생성 시, 주문 등록시간을 기록한다..")
    void registeredDateTime() {
        // given
        LocalDateTime registeredDateTime = LocalDateTime.now();

        Product product1 = this.createProduct("001", 2000);
        Product product2 = this.createProduct("002", 4000);
        List<Product> products = List.of(
                product1, product2
        );

        // when
        Order order = Order.create(products, registeredDateTime);

        // then
        assertThat(order.getRegisteredDataTime()).isEqualTo(registeredDateTime);

    }

    private Product createProduct(
            String productNumber,
            int price
    ){
        return Product.builder()
                .type(ProductType.HANDMADE)
                .productNumber(productNumber)
                .price(price)
                .sellingStatus(ProductSellingStatus.SELLING)
                .name("메뉴명")
                .build();
    }



}

