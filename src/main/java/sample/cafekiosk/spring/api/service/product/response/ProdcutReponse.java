package sample.cafekiosk.spring.api.service.product.response;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;
import sample.cafekiosk.spring.domain.product.ProductType;

@Getter
public class ProdcutReponse {
    private Long id;

    private String productNumber;

    private ProductType productType;


    private ProductSellingStatus sellingStatus ;

    private String name;

    private int price;

    @Builder
    private ProdcutReponse(Long id, String productNumber, ProductType productType, ProductSellingStatus sellingStatus, String name, int price) {
        this.id = id;
        this.productNumber = productNumber;
        this.productType = productType;
        this.sellingStatus = sellingStatus;
        this.name = name;
        this.price = price;
    }

    public static ProdcutReponse of(Product product) {
        return ProdcutReponse.builder()
                .id(product.getId())
                .productNumber(product.getProductNumber())
                .productType(product.getType())
                .sellingStatus(product.getSellingStatus())
                .name(product.getName())
                .price(product.getPrice())
                .build();
    };
}
