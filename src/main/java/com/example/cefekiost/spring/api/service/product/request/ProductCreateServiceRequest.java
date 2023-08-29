package com.example.cefekiost.spring.api.service.product.request;

import com.example.cefekiost.spring.domain.product.Product;
import com.example.cefekiost.spring.domain.product.ProductSellingStatus;
import com.example.cefekiost.spring.domain.product.ProductType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductCreateServiceRequest {

    private ProductType productType;
    private ProductSellingStatus sellingStatus;
    private String name;
    private int price;

    @Builder
    private ProductCreateServiceRequest(ProductType productType, ProductSellingStatus sellingStatus, String name, int price) {
        this.productType = productType;
        this.sellingStatus = sellingStatus;
        this.name = name;
        this.price = price;
    }

    public Product toEntity(String nextProductNumber) {
        return Product.builder()
                .productNumber(nextProductNumber)
                .productType(productType)
                .productSellingStatus(sellingStatus)
                .name(name)
                .price(price)
                .build();
    }

}