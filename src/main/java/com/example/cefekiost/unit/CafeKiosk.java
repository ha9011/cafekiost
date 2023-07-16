package com.example.cefekiost.unit;

import com.example.cefekiost.unit.beverage.Beverage;
import com.example.cefekiost.unit.order.Order;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class CafeKiosk {

    private final List<Beverage> beverages = new ArrayList<>();

    public void add(Beverage beverage){
        beverages.add(beverage);
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

    public Order createOrder(){
        return new Order(LocalDateTime.now(), beverages);
    }
}
