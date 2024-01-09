package sample.cafekiosk.spring.api.controller.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import sample.cafekiosk.spring.api.service.product.ProductService;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(controllers = ProductController.class) // 컨트롤러 관련된 빈만 띄우는 어노테이션
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean // 가짜객체 빈을 컨테이너에 등록하게 해줌 (ProductController 에 격납된 객체들과 동일하게...)
    private ProductService productService;

    @Test
    @DisplayName("신규상품을 등록한다.")
    void createProduct() {
        // given

        // when

        // then
    }

}