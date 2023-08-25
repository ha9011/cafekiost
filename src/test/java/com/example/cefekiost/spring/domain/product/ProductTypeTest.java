package com.example.cefekiost.spring.domain.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProductTypeTest {

    // 타입이 언제 변경될지 모르니 테스트 해두는게 좋음
    @Test
    @DisplayName("상품 타입이 재고 관련 타입인지를 체크한다.")
    public void containsStockType_Fail() throws Exception{
        //given
        ProductType bakery = ProductType.HANDMADE;

        //when
        boolean result = ProductType.containsStockType(bakery);

        //then
        assertThat(result).isFalse();

    }
    // 타입이 언제 변경될지 모르니 테스트 해두는게 좋음
    @Test
    @DisplayName("상품 타입이 재고 관련 타입인지를 체크한다.")
    public void containsStockType_True() throws Exception{
        //given
        ProductType bakery = ProductType.BAKERY;

        //when
        boolean result = ProductType.containsStockType(bakery);

        //then
        assertThat(result).isTrue();

    }
}