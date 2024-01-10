package sample.cafekiosk.spring.api.controller.product.request;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;
import sample.cafekiosk.spring.domain.product.ProductType;

@Getter
@NoArgsConstructor
public class CreateProductRequest {

    @NotNull(message = "상품타입을 입력해주세요.")
    private ProductType type;

    @NotNull(message = "상품판매상태를 입력해주세요.")
    private ProductSellingStatus sellingStatus ;

    @NotNull(message = "상품명을 입력해주세요.")
    private String name;

    @Positive(message = "상품가격을 0원이상으로 입력해주세요.")
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
