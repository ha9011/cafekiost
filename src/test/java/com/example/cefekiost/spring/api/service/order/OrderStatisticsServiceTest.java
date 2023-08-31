package com.example.cefekiost.spring.api.service.order;

import com.example.cefekiost.spring.api.service.mail.MailService;
import com.example.cefekiost.spring.client.mail.MailSendClient;
import com.example.cefekiost.spring.domain.history.mail.MailSendHistory;
import com.example.cefekiost.spring.domain.history.mail.MailSendHistoryRepository;
import com.example.cefekiost.spring.domain.order.Order;
import com.example.cefekiost.spring.domain.order.OrderRepository;
import com.example.cefekiost.spring.domain.order.OrderStatus;
import com.example.cefekiost.spring.domain.orderProduct.OrderProductRepository;
import com.example.cefekiost.spring.domain.product.Product;
import com.example.cefekiost.spring.domain.product.ProductRepository;
import com.example.cefekiost.spring.domain.product.ProductType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.example.cefekiost.spring.domain.product.ProductSellingStatus.SELLING;
import static com.example.cefekiost.spring.domain.product.ProductType.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ActiveProfiles("test")
class OrderStatisticsServiceTest {
    
    @Autowired
    private OrderStatisticsService orderStatisticsService;
    
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;
    
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MailSendHistoryRepository mailSendHistoryRepository;

    @MockBean
    private MailSendClient mailSendClient;

    @AfterEach
    void tearDown(){
        orderProductRepository.deleteAllInBatch();
        //orderRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
        mailSendHistoryRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("결제완료 주문들을 조회하여 매출 통계 메일을 전송한다.")
    public void sendOrderStatisticsMail() throws Exception{
        //given
        Product product1 = createProduct(HANDMADE, "001", 1000);
        Product product2 = createProduct(HANDMADE, "002", 2000);
        Product product3 = createProduct(HANDMADE, "003", 3000);
        productRepository.saveAll(List.of(product1, product2, product3));

        LocalDateTime now = LocalDateTime.of(2023,3,5,0,0);

        List<Product> productList = List.of(product1, product2, product3);

        Order order1 = createOrderPaymentComplete(LocalDateTime.of(2023,3,4,23,59, 59), productList);
        Order order2 = createOrderPaymentComplete(now, productList);
        Order order3 = createOrderPaymentComplete(LocalDateTime.of(2023,3,5,23,59,59), productList);
        Order order4 = createOrderPaymentComplete(LocalDateTime.of(2023,3,6,0,0), productList);

        // 행위를 설정
        // stubbing
        Mockito.when(mailSendClient.sendEmail(any(String.class),any(String.class),any(String.class),any(String.class)))
                .thenReturn( true );
        //when
        boolean result = orderStatisticsService.sendOrderStatisticsMail(LocalDate.of(2023, 3, 5), "test@co.kr");

        //then
        assertThat(result).isTrue();
        List<MailSendHistory> histories = mailSendHistoryRepository.findAll();
        assertThat(histories).hasSize(1)
                .extracting("content")
                .contains("총 매출 합계는 12000원 입니다.");
    }

    private Order createOrderPaymentComplete(LocalDateTime now, List<Product> productList) {
        Order order1 = Order.builder()
                .products(productList)
                .orderStatus(OrderStatus.PAYMENT_COMPLETED)
                .registeredDateTime(now)
                .build();
        return orderRepository.save(order1);
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