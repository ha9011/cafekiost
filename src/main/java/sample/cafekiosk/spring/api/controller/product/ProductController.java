package sample.cafekiosk.spring.api.controller.product;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sample.cafekiosk.spring.api.ApiResponse;
import sample.cafekiosk.spring.api.controller.product.request.CreateProductRequest;
import sample.cafekiosk.spring.api.service.product.ProductService;
import sample.cafekiosk.spring.api.service.product.response.ProductReponse;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProductController {

    private final ProductService productService;

    @PostMapping("/api/v1/products/new")
    public ApiResponse<ProductReponse> createProduct(@RequestBody @Valid CreateProductRequest req){

        return ApiResponse.ok(productService.createProduct(req));

    }

    @GetMapping("/api/v1/products/selling")
    public ApiResponse<List<ProductReponse>> getSellingProducts(){

        return ApiResponse.ok(productService.getSellingProducts());
    }


}
