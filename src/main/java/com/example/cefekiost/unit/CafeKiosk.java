package com.example.cefekiost.unit;

import com.example.cefekiost.unit.beverage.Beverage;
import com.example.cefekiost.unit.order.Order;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class CafeKiosk {

    private final List<Beverage> beverages = new ArrayList<>();
    public static final LocalTime SHOP_OPEN_TIME =  LocalTime.of(10, 0);
    public static final LocalTime SHOP_CLOSE_TIME = LocalTime.of(22, 0);

    public void add(Beverage beverage, int count){
        if(count <= 0){
            throw new IllegalArgumentException("음료는 1잔 이상 주문하실 수 있습니다");
        }

        for(int i = 0 ; i < count ; i++){
            beverages.add(beverage);
        }
    }
    public void remove(Beverage beverage){
        beverages.remove(beverage);  // object 비교가 되나? 2개 넣으면 어떻게 될지 확인해보자
    }

    public void allRemove(){
        beverages.clear();
    }

    public int calculateTotalPrice() {
        return beverages.stream().mapToInt(e->e.getPrice()).sum();
    };

    public Order createOrder(LocalDateTime currentDateTime){

        System.out.println("SHOP_OPEN_TIME : "+ SHOP_OPEN_TIME);
        System.out.println("SHOP_CLOSE_TIME : "+ SHOP_CLOSE_TIME);

        //LocalDateTime currentDateTime = LocalDateTime.now();
        LocalTime currentTime = currentDateTime.toLocalTime();

        System.out.println("currentDateTime : " + currentDateTime);
        System.out.println("currentTime : " + currentTime);

        if(currentTime.isBefore(SHOP_OPEN_TIME) || currentTime.isAfter(SHOP_CLOSE_TIME)){
            throw new IllegalArgumentException("주문 시간이 아닙니다. 관리자에게 문의하세요");
        }
        return new Order(LocalDateTime.now(), beverages);
    }
}
