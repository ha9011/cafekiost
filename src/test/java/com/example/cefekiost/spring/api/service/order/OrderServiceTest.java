package com.example.cefekiost.spring.api.service.order;

import com.example.cefekiost.spring.api.service.order.request.OrderCreateRequest;
import com.example.cefekiost.spring.api.service.order.response.OrderResponse;
import com.example.cefekiost.spring.domain.order.OrderRepository;
import com.example.cefekiost.spring.domain.orderProduct.OrderProductRepository;
import com.example.cefekiost.spring.domain.product.Product;
import com.example.cefekiost.spring.domain.product.ProductRepository;
import com.example.cefekiost.spring.domain.product.ProductType;
import com.example.cefekiost.spring.domain.stock.Stock;
import com.example.cefekiost.spring.domain.stock.StockRepository;
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
import static com.example.cefekiost.spring.domain.product.ProductType.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.InstanceOfAssertFactories.type;
import static org.assertj.core.groups.Tuple.tuple;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest // 이놈은 트랜잭션이 안걸려 있고, @DataJpaTest에는 트랜잭션이 걸려있음
@ActiveProfiles("test")
//@Transactional // 이렇게 추가 할 경우 문제점이 뭘까??
class OrderServiceTest {

    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderProductRepository orderProductRepository;
    @Autowired
    private StockRepository stockRepository;
    @AfterEach
    void tearDown() {
        orderProductRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
        orderRepository.deleteAllInBatch();
        stockRepository.deleteAllInBatch();

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

    @Test
    @DisplayName("재고와 관련된 상품이 포함되어 있는 주문번호 리스트를 받아 주문을 생성한다.")
    public void createOrderWithStock() throws Exception{
        //given

        Product product1 = createProduct(BOTTLE, "001", 1000);
        Product product2 = createProduct(BAKERY, "002", 3000);
        Product product3 = createProduct(HANDMADE, "003", 5000);
        productRepository.saveAll(List.of(product1, product2, product3));


        OrderCreateRequest request = OrderCreateRequest.builder()
                .productNumbers(List.of("001","001", "002","003"))
                .build();

        Stock stock1 = Stock.create("001", 2);
        Stock stock2 = Stock.create("002", 2);

        List<Stock> savedStocks = stockRepository.saveAll(List.of(stock1, stock2));

        LocalDateTime orderRegisteredDateTime = LocalDateTime.now();
        //when
        OrderResponse orderResponse = orderService.createOrder(request,orderRegisteredDateTime);

        //then
        // red -> green -
        assertThat(orderResponse.getId()).isNotNull();
        assertThat(orderResponse)
                .extracting("registeredDateTime", "totalPrice")
                .contains(orderRegisteredDateTime, 10000);
        assertThat(orderResponse.getProducts()).hasSize(4)
                .extracting("productNumber", "price")
                .containsExactlyInAnyOrder(
                        tuple("001",1000),
                        tuple("001",1000),
                        tuple("002",3000),
                        tuple("003",5000)

                );

        List<Stock> stocks = stockRepository.findAll();
        assertThat(stocks).hasSize(2)
                .extracting("productNumber", "quantity")
                .containsExactlyInAnyOrder(
                        tuple("001",0),
                        tuple("002",1)
                );

    }

    @Test
    @DisplayName("재고가 부족한 상품으로 주문을 생성하려는 경우 예외가 발생한다.")
    public void createOrderWithNoStock() throws Exception{
        //given

        Product product1 = createProduct(BOTTLE, "001", 1000);
        Product product2 = createProduct(BAKERY, "002", 3000);
        Product product3 = createProduct(HANDMADE, "003", 5000);
        productRepository.saveAll(List.of(product1, product2, product3));


        OrderCreateRequest request = OrderCreateRequest.builder()
                .productNumbers(List.of("001","001", "002","003"))
                .build();

        Stock stock1 = Stock.create("001", 0); // 재고 없음
        Stock stock2 = Stock.create("002", 2);

        List<Stock> savedStocks = stockRepository.saveAll(List.of(stock1, stock2));

        LocalDateTime orderRegisteredDateTime = LocalDateTime.now();
        //when
        //OrderResponse orderResponse = orderService.createOrder(request,orderRegisteredDateTime);

        assertThatThrownBy(()->orderService.createOrder(request,orderRegisteredDateTime))
                .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("재고가 부족한 상품이 있습니다.");


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