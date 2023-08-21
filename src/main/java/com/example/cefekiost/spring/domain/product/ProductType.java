package com.example.cefekiost.spring.domain.product;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductType {

    HANDMADE("핸드메이드"),
    BOTTLE("병음료"),
    BAKERY("빵");

    private final String text;
}
