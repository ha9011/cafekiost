package sample.cafekiosk.spring.api.service.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sample.cafekiosk.spring.api.controller.product.request.CreateProductRequest;
import sample.cafekiosk.spring.api.service.product.response.ProductReponse;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;
import sample.cafekiosk.spring.domain.product.ProductType;

import java.util.List;
import java.util.stream.Collectors;

/**
 * readOnly = true : 읽기전용
 * curd 에서 cud 동작 X
 * Jpa : cud 스냅샷 저장 X, 변경감지 X (성능 향상)
 * CQRS - command / read 분리
 */
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductReponse> getSellingProducts(){
        List<Product> products = productRepository.findAllBySellingStatusIn(ProductSellingStatus.forDisplay());

        return products.stream().map(ProductReponse::of).collect(Collectors.toList());
    }

    @Transactional
    public ProductReponse createProduct(CreateProductRequest req) {
        // productNumber 생성
        // 001, 002, 003, 004 ...
        String nextProductNumber = createNextProductNumber();

        Product product = req.toEntity(nextProductNumber);
        Product savedProduct = productRepository.save(product);

        return ProductReponse.builder()
                .productNumber(savedProduct.getProductNumber())
                .type(savedProduct.getType())
                .sellingStatus(savedProduct.getSellingStatus())
                .name(savedProduct.getName())
                .price(savedProduct.getPrice())
                .build();
    }

    private String createNextProductNumber(){
        String latestProductNumber = productRepository.findLastestProductNumber();
        if(latestProductNumber == null){
            return "001";
        }
        int nextProductNumberInt = Integer.parseInt(latestProductNumber) + 1;
        return String.format("%03d", nextProductNumberInt);


    }

}
