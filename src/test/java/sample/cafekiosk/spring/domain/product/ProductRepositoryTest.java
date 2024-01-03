package sample.cafekiosk.spring.domain.product;

import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest // 통합 테스트 전용, 즉 스프링부트 서버를 실행해서 테스트를 하는데
@DataJpaTest // 이 친구도 마찬가지인데!? JPA 관련된 애들만 띄어서 테스트를 하기때문에 위 친구보다 가볍다.
@ActiveProfiles("test")
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("원하는 판매상태를 가진 상품들을 조회한다.")
    void findAllBySellingStatusIn() {
        // given
        Product product1 = Product.builder()
                .price(4000)
                .sellingStatus(ProductSellingStatus.SELLING)
                .productNumber("001")
                .type(ProductType.HANDMADE)
                .name("아메리카노")
                .build();

        Product product2 = Product.builder()
                .price(4500)
                .sellingStatus(ProductSellingStatus.HOLD)
                .productNumber("002")
                .type(ProductType.HANDMADE)
                .name("카페라떼")
                .build();

        Product product3 = Product.builder()
                .price(7000)
                .sellingStatus(ProductSellingStatus.STOP_SELLING)
                .productNumber("003")
                .type(ProductType.HANDMADE)
                .name("팥빙수")
                .build();

        productRepository.saveAll(List.of(product1, product2, product3));

        //when
        List<Product> products = productRepository.findAllBySellingStatusIn(List.of(ProductSellingStatus.SELLING, ProductSellingStatus.HOLD));

        // then
        assertThat(products).hasSize(2)
                .extracting("productNumber", "name", "sellingStatus")
                .containsExactlyInAnyOrder(
                        tuple("001", "아메리카노", ProductSellingStatus.SELLING),
                        tuple("002", "카페라떼", ProductSellingStatus.HOLD)
                );
    }

}