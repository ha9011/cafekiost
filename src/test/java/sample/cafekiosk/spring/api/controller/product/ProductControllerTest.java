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
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;
import sample.cafekiosk.spring.domain.product.ProductType;

import static org.junit.jupiter.api.Assertions.*;
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
                    post("/api/v1/products/newe")
                        .content(objectMapper.writeValueAsString(req))
                        .contentType(APPLICATION_JSON)

                ).andDo(print()) // 로그를 자세히 볼수있다.
                .andExpect(status().isOk());
        // when

        // then
    }

}