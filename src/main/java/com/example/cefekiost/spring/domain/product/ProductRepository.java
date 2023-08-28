package com.example.cefekiost.spring.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {


    /**
     * select *
     * from product
     * where selling_type in ("selling', 'hold')
     */
    List<Product> findAllByProductSellingStatusIn(List<ProductSellingStatus> sellingTypes);

    List<Product> findAllByProductNumberIn(List<String> productNumber);

    @Query(value = "SELECT product_number FROM product p order by id desc limit 1", nativeQuery = true)
    Optional<String> findLatestProduct();
}
