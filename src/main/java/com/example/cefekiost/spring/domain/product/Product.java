package com.example.cefekiost.spring.domain.product;

import com.example.cefekiost.spring.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ProductType type;

    @Enumerated(EnumType.STRING)
    private ProductSellingStatus sellingStatus;

    private String productNumber;

    private String name;

    private int price;

    @Builder
    private Product(ProductType productType, ProductSellingStatus productSellingStatus, String productNumber, String name, int price) {
        this.type = productType;
        this.sellingStatus = productSellingStatus;
        this.productNumber = productNumber;
        this.name = name;
        this.price = price;
    }
}
