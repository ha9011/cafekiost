package com.example.cefekiost.spring.api.service.product.response;

import com.example.cefekiost.spring.domain.product.Product;
import com.example.cefekiost.spring.domain.product.ProductSellingStatus;
import com.example.cefekiost.spring.domain.product.ProductType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductResponse {
    private Long id;

    private ProductType productType;

    private ProductSellingStatus productSellingStatus;

    private String productNumber;

    private String name;

    private int price;

    @Builder
    private ProductResponse( Long id, ProductType productType, ProductSellingStatus productSellingStatus, String productNumber, String name, int price) {
        this.id = id;
        this.productType = productType;
        this.productSellingStatus = productSellingStatus;
        this.productNumber = productNumber;
        this.name = name;
        this.price = price;
    }

    public static ProductResponse of(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .productSellingStatus(product.getProductSellingStatus())
                .productNumber(product.getProductNumber())
                .price(product.getPrice())
                .productType(product.getProductType())
                .build();
    };
}
