package sample.cafekiosk.spring.api.service.product;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import sample.cafekiosk.spring.api.controller.product.request.CreateProductRequest;
import sample.cafekiosk.spring.api.service.product.response.ProductReponse;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;
import sample.cafekiosk.spring.domain.product.ProductType;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")

class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @AfterEach
    void tearDown() {
        productRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("신규 상품을 등록한다. 상품번호는 가장 최근 상품의 상품번호에서 1 증가한 값이다.")
    void createProduct() {

        // given
        Product product1 = createProduct("001", "아메리카노");

        productRepository.saveAll(List.of(product1));

        //WHEN
        CreateProductRequest req = CreateProductRequest.builder()
                .price(5000)
                .type(ProductType.HANDMADE)
                .sellingStatus(ProductSellingStatus.SELLING)
                .name("카푸치노")
                .build();

        ProductReponse productReponse = productService.createProduct(req);
        List<Product> products = productRepository.findAll();
        //THEN
        assertThat(productReponse).extracting("productNumber", "type", "sellingStatus", "name", "price")
                .containsExactlyInAnyOrder("002", ProductType.HANDMADE, ProductSellingStatus.SELLING, "카푸치노", 5000);
        assertThat(products).hasSize(2)
                .extracting("productNumber", "type", "sellingStatus", "name", "price")
                .containsExactlyInAnyOrder(
                        tuple("001", ProductType.BAKERY, ProductSellingStatus.SELLING, "아메리카노", 7000),
                        tuple("002", ProductType.HANDMADE, ProductSellingStatus.SELLING, "카푸치노", 5000)
                );



    }

    @Test
    @DisplayName("신규 상품을 등록한다. 상품번호는 가장 최근 상품의 상품번호에서 1 증가한 값이다." +
            "그러나 상품이 없는 경우 001로 시작한다.")
    void createProduct2() {

        // given

        //WHEN
        CreateProductRequest req = CreateProductRequest.builder()
                .price(5000)
                .type(ProductType.HANDMADE)
                .sellingStatus(ProductSellingStatus.SELLING)
                .name("카푸치노")
                .build();

        ProductReponse productReponse = productService.createProduct(req);
        List<Product> products = productRepository.findAll();
        //THEN
        //THEN
        assertThat(productReponse).extracting("productNumber", "type", "sellingStatus", "name", "price")
                .containsExactlyInAnyOrder("001", ProductType.HANDMADE, ProductSellingStatus.SELLING, "카푸치노", 5000);

        assertThat(products).hasSize(1)
                .extracting("productNumber", "type", "sellingStatus", "name", "price")
                .containsExactlyInAnyOrder(
                        tuple("001", ProductType.HANDMADE, ProductSellingStatus.SELLING, "카푸치노", 5000)
                        );

    }

    static Product createProduct(String productNumber, String name) {
        return Product.builder()
                .price(7000)
                .sellingStatus(ProductSellingStatus.SELLING)
                .productNumber(productNumber)
                .type(ProductType.BAKERY)
                .name(name)
                .build();
    }


}