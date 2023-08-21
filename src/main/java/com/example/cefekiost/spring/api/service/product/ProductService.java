package com.example.cefekiost.spring.api.service.product;


import com.example.cefekiost.spring.api.service.product.response.ProductResponse;
import com.example.cefekiost.spring.domain.product.Product;
import com.example.cefekiost.spring.domain.product.ProductRepository;
import com.example.cefekiost.spring.domain.product.ProductSellingStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductResponse> getSellingProducts(){
        List<Product> products = productRepository.findAllBySellingStatusIn(ProductSellingStatus.getSellingProducts());

        return products.stream().map(ProductResponse::of)
                .collect(Collectors.toList());
    }
}
