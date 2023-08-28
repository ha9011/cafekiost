package com.example.cefekiost.spring.api.service.product;

import com.example.cefekiost.spring.api.controller.product.dto.ProductCreateRequest;
import com.example.cefekiost.spring.api.service.product.response.ProductResponse;
import com.example.cefekiost.spring.domain.product.Product;
import com.example.cefekiost.spring.domain.product.ProductRepository;
import com.example.cefekiost.spring.domain.product.ProductSellingStatus;
import com.example.cefekiost.spring.domain.product.ProductType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.cefekiost.spring.domain.product.ProductSellingStatus.*;
import static com.example.cefekiost.spring.domain.product.ProductType.HANDMADE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ProductServiceTest {
    @Autowired
    private ProductService productService;


    @Autowired
    private ProductRepository productRepository;


    @Test
    @DisplayName("신규 상품을 등록한다. 상품번호는 가장 최근 상품의 상품번호에서 1증가한 값이다.")
    public void createProduct() throws Exception{
        //given
        Product product1 = createProduct("001", HANDMADE, SELLING, "아메리카노", 4000);
        productRepository.saveAll(List.of(product1));

        ProductCreateRequest request = ProductCreateRequest.builder()
                .productType(HANDMADE)
                .sellingStatus(SELLING)
                .name("카푸치노")
                .price(5000)
                .build();

        //when
        ProductResponse productResponse = productService.createProduct(request);

        List<Product> products = productRepository.findAll();

        //then
        assertThat(productResponse)
                .extracting("productNumber", "productType", "productSellingStatus", "name", "price")
                .contains("002", HANDMADE, SELLING, "카푸치노", 5000);

        assertThat(products).hasSize(2)
                .extracting("productNumber", "productType", "productSellingStatus", "name", "price")
                .containsExactlyInAnyOrder(
                        tuple("001", HANDMADE, SELLING, "아메리카노", 4000),
                        tuple("002", HANDMADE, SELLING, "카푸치노", 5000)
                );
    }

    @Test
    @DisplayName("신규 상품을 등록한다. 기존상품이 없을 경우 상품번호는 '001'부터 시작한다.")
    public void createProductWhenProductIsEmpty() throws Exception{
        //given

        ProductCreateRequest request = ProductCreateRequest.builder()
                .productType(HANDMADE)
                .sellingStatus(SELLING)
                .name("카푸치노")
                .price(5000)
                .build();

        //when
        ProductResponse productResponse = productService.createProduct(request);

        List<Product> products = productRepository.findAll();

        //then
        assertThat(productResponse)
                .extracting("productNumber", "productType", "productSellingStatus", "name", "price")
                .contains("001", HANDMADE, SELLING, "카푸치노", 5000);

        assertThat(products).hasSize(1)
                .extracting("productNumber", "productType", "productSellingStatus", "name", "price")
                .containsExactlyInAnyOrder(
                        tuple("001", HANDMADE, SELLING, "카푸치노", 5000)
                );
    }

    private Product createProduct(String productNumber, ProductType type, ProductSellingStatus sellingStatus, String name, int price) {
        return Product.builder()
                .productNumber(productNumber)
                .productType(type)
                .productSellingStatus(sellingStatus)
                .name(name)
                .price(price)
                .build();
    }
}