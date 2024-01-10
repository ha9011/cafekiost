package sample.cafekiosk.spring.api.controller.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import sample.cafekiosk.spring.api.controller.order.request.OrderCreateReq;
import sample.cafekiosk.spring.api.service.order.OrderService;
import sample.cafekiosk.spring.api.service.order.response.OrderResponse;
import sample.cafekiosk.spring.domain.order.Order;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;
import sample.cafekiosk.spring.domain.product.ProductType;

import java.time.LocalDateTime;
import java.util.List;

import static java.nio.file.Paths.get;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("커피 주문을 생성합니다.")
    void createOrder() throws Exception {
        // given
        OrderCreateReq req = OrderCreateReq.builder()
                .productNumbers(List.of("001","002"))
                .build();

        // TODO 이 부분 왜 안되는지 찾아보자. -> mock 으로 만들어진 객체의 결과값은 null 이란다.
        Order order = new Order(List.of(new Product("001", ProductType.HANDMADE, ProductSellingStatus.SELLING,"테스트", 1000)), LocalDateTime.now());
        OrderResponse result = OrderResponse.of(order);
        System.out.println("-------result------");
        System.out.println(result);
        when(orderService.createOrder(req, LocalDateTime.now())).thenReturn(result);
        // when // then
        mockMvc.perform(post("/api/v1/orders/new")
                        .content(objectMapper.writeValueAsString(req))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.httpStatus").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data").isNotEmpty()); // 가짜객체라 결과값은 알수없음.
                //
    }
    @Test
    @DisplayName("커피 주문을 생성하는데, 상품번호 리스트가 없을 경우")
    void createOrderWithoutProductNumbers() throws Exception {
        // given
        OrderCreateReq req = OrderCreateReq.builder()
                .productNumbers(null)
                .build();



        // when // then
        mockMvc.perform(post("/api/v1/orders/new")
                        .content(objectMapper.writeValueAsString(req))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.httpStatus").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("상품번호 리스트는 필수입니다."))
                .andExpect(jsonPath("$.data").isEmpty()); // 가짜객체라 결과값은 알수없음.

    }

}