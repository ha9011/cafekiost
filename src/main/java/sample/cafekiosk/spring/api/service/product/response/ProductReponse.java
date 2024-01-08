package sample.cafekiosk.spring.api.service.product.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;
import sample.cafekiosk.spring.domain.product.ProductType;

@Getter
@ToString
public class ProductReponse {
    private Long id;

    private String productNumber;

    private ProductType productType;

    private ProductSellingStatus sellingStatus ;

    private String name;

    private int price;

    @Builder
    private ProductReponse(Long id, String productNumber, ProductType productType, ProductSellingStatus sellingStatus, String name, int price) {
        this.id = id;
        this.productNumber = productNumber;
        this.productType = productType;
        this.sellingStatus = sellingStatus;
        this.name = name;
        this.price = price;
    }

    public static ProductReponse of(Product product) {
        return ProductReponse.builder()
                .id(product.getId())
                .productNumber(product.getProductNumber())
                .productType(product.getType())
                .sellingStatus(product.getSellingStatus())
                .name(product.getName())
                .price(product.getPrice())
                .build();
    };
}
