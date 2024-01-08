package sample.cafekiosk.spring.domain.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProductTypeTest {

    @Test
    @DisplayName("상품타입이 재고관련 타입인지를 체크")
    void withStockType() {
        // given
        ProductType givenType = ProductType.HANDMADE;
        System.out.println("---");
        System.out.println(givenType);

        // when
        boolean result = ProductType.withStockType(givenType);

        // then
        assertThat(result).isEqualTo(false);
        assertThat(result).isFalse();

    }

    @Test
    @DisplayName("상품타입이 재고관련 타입인지를 체크")
    void withStockType2() {
        // given
        ProductType givenType = ProductType.BAKERY;
        System.out.println("---");
        System.out.println(givenType);

        // when
        boolean result = ProductType.withStockType(givenType);

        // then
        assertThat(result).isEqualTo(true);
        assertThat(result).isTrue();

    }

}