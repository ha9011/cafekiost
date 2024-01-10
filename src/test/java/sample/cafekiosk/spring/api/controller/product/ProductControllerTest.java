package sample.cafekiosk.spring.api.controller.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import sample.cafekiosk.spring.api.controller.product.request.CreateProductRequest;
import sample.cafekiosk.spring.api.service.product.ProductService;
import sample.cafekiosk.spring.api.service.product.response.ProductReponse;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;
import sample.cafekiosk.spring.domain.product.ProductType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ProductController.class) // 컨트롤러 관련된 빈만 띄우는 어노테이션
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc; // 자동으로 MockMvc를 주입받을 수 있는데, 이떼, @WebMvcTest에 지정된 컨트롤러로 지정됨

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean // 가짜객체 빈을 컨테이너에 등록하게 해줌 (ProductController 에 격납된 객체들과 동일하게...)
    private ProductService productService;

    @Test
    @DisplayName("신규상품을 등록한다.")
    void createProduct() throws Exception {
        // given
        CreateProductRequest req = CreateProductRequest.builder()
                .type(ProductType.HANDMADE)
                .sellingStatus(ProductSellingStatus.SELLING)
                .name("아메리카노")
                .price(4000)
                .build();

        ResultActions resultActions = mockMvc.perform(
                    post("/api/v1/products/new")
                        .content(objectMapper.writeValueAsString(req))
                        .contentType(APPLICATION_JSON)

                ).andDo(print()) // 로그를 자세히 볼수있다.
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.httpStatus").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data").isEmpty()); // 가짜객체라 결과값은 알수없음.
        // when

        // then
    }

    @Test
    @DisplayName("상품들을 조회한다.")
    void getSellingProduct() throws Exception {
        // given
        List<ProductReponse> result = List.of();
        System.out.println("result : " + result.toString());
        when(productService.getSellingProducts()).thenReturn(result);
        // 실제 컨트롤러에서 쓰인 빈(productService)을 mock으로 만들어서 해당
        // 메소드의 값을 가짜로 만든다.(성공이란 전제하니깐..)
        // 다시 말하지만, controller 단에 관련된 것만 테스트 할뿐, 서비스 단은 이미 테스트 했다.

        // when
        // then
        ResultActions resultActions = mockMvc.perform(
                        get("/api/v1/products/selling")
                                //.queryParam() 쿼리스트링
                ).andDo(print()) // 로그를 자세히 볼수있다.
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.httpStatus").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data").isArray()); // 가짜(mock)객체라 결과값은 알수없음.

    }

    @Test
    @DisplayName("신규 상품을 등록할 때, 상품 타입은 필수값 입니다.") // TODO 필드값 모두 해보기!
    void createProductWithoutType() throws Exception {
        // given
        CreateProductRequest req = CreateProductRequest.builder()
                .sellingStatus(ProductSellingStatus.SELLING)
                .name("아메리카노")
                .price(4000)
                .build();

        ResultActions resultActions = mockMvc.perform(
                        post("/api/v1/products/new")
                                .content(objectMapper.writeValueAsString(req))
                                .contentType(APPLICATION_JSON)

                ).andDo(print()) // 로그를 자세히 볼수있다.
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.httpStatus").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("상품타입을 입력해주세요."))
                .andExpect(jsonPath("$.data").isEmpty());

                // JSON으로 읽혀야 하기 때문에 ApiResponse에 @getter가 있어야하고
                // advice exception Handler에 @restControllerAdvice 가 있어야한다.

        // when

        // then
    }

}