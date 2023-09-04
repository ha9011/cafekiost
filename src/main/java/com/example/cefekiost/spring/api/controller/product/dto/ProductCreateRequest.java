package com.example.cefekiost.spring.api.controller.product.dto;

import com.example.cefekiost.spring.api.service.product.request.ProductCreateServiceRequest;
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
public class ProductCreateRequest {

    @NotNull(message = "상품 타입은 필수입니다.")
    private ProductType productType;

    @NotNull(message = "상품 판매상태는 필수입니다.")
    private ProductSellingStatus sellingStatus;

    @NotBlank(message = "상품 이름은 필수입니다.")
    private String name;

    @Positive(message = "상품 가격은 양수여야 합니다.")
    private int price;

    @Builder
    private ProductCreateRequest(ProductType productType, ProductSellingStatus sellingStatus, String name, int price) {
        this.productType = productType;
        this.sellingStatus = sellingStatus;
        this.name = name;
        this.price = price;
    }

    public ProductCreateServiceRequest toServiceRequest() {
        return ProductCreateServiceRequest.builder()
                .productType(productType)
                .sellingStatus(sellingStatus)
                .name(name)
                .price(price)
                .build();
    }

}