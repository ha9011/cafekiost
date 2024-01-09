package sample.cafekiosk.spring.api.controller.product.request;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;
import sample.cafekiosk.spring.domain.product.ProductType;

@Getter
public class CreateProductRequest {

    private ProductType type;

    private ProductSellingStatus sellingStatus ;

    private String name;

    private int price;

    @Builder
    public CreateProductRequest(ProductType type, ProductSellingStatus sellingStatus, String name, int price) {
        this.type = type;
        this.sellingStatus = sellingStatus;
        this.name = name;
        this.price = price;
    }


    public Product toEntity(String nextProductNumber) {
        return Product.builder()
                .name(this.getName())
                .productNumber(nextProductNumber)
                .type(this.getType())
                .sellingStatus(this.getSellingStatus())
                .price(this.getPrice())
                .build();
    }
}
