package com.example.cefekiost.unit;

import com.example.cefekiost.unit.beverage.Americano;
import com.example.cefekiost.unit.beverage.Latte;
import com.example.cefekiost.unit.order.Order;

import java.time.LocalDateTime;

public class CafeKioskRunner {

    public static void main(String[] args) {
        CafeKiosk cafeKiosk = new CafeKiosk(); // 키오스트 생성

        cafeKiosk.add(new Americano(),1);
        System.out.println(">>> 아메리카노 추가");


        cafeKiosk.add(new Americano(),1);
        System.out.println(">>> 아메리카노 추가");

        cafeKiosk.add(new Latte(),1);
        System.out.println(">>> 라떼 추가");



        int totalPrice = cafeKiosk.calculateTotalPrice();
        System.out.println("총 주문 가격 : " + totalPrice);

        cafeKiosk.createOrder(LocalDateTime.now());


    }
}
