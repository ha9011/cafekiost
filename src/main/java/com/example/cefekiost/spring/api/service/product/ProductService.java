package com.example.cefekiost.spring.api.service.product;


import com.example.cefekiost.spring.api.controller.product.dto.ProductCreateRequest;
import com.example.cefekiost.spring.api.service.product.request.ProductCreateServiceRequest;
import com.example.cefekiost.spring.api.service.product.response.ProductResponse;
import com.example.cefekiost.spring.domain.product.Product;
import com.example.cefekiost.spring.domain.product.ProductRepository;
import com.example.cefekiost.spring.domain.product.ProductSellingStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    
    // 동시성 이슈가 있을 수 있음(동시 등록할 경우)
    public ProductResponse createProduct(ProductCreateServiceRequest request) {
        // productNumber
        // 001, 002, 003, 004
        // DB에서 마지막 저장된 ProductNumber에서 +1 씩 추가
        String nextProductNumber = createNextProductNumber();


        Product product = request.toEntity(nextProductNumber);
        Product newProduct = productRepository.save(product);
        ProductResponse result = ProductResponse.of(newProduct);
        return result;
    }

    private String createNextProductNumber(){
        String maxProductNumber = productRepository.findLatestProduct().orElse(null);
        if(maxProductNumber==null) return "001";

        int newProductNumberInt = Integer.parseInt(maxProductNumber) + 1;

        return String.format("%03d", newProductNumberInt);
    };
    public List<ProductResponse> getSellingProducts(){
        List<Product> products = productRepository.findAllByProductSellingStatusIn(ProductSellingStatus.getSellingProducts());

        return products.stream().map(ProductResponse::of)
                .collect(Collectors.toList());
    }
}
