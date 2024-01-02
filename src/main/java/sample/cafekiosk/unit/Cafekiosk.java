package sample.cafekiosk.unit;

import sample.cafekiosk.unit.order.Order;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Cafekiosk {

    private static final LocalTime SHOP_OPEN_TIME = LocalTime.of(10,0);
    private static final LocalTime SHOP_CLOSE_TIME = LocalTime.of(22,0);

    private final List<Beverage> beverages = new ArrayList<>();

    public void add(Beverage beverage){
        beverages.add(beverage);
        System.out.println(beverage.getName() + "이(가) 추가 되었습니다.");
    }
    public void add(Beverage beverage, int count){

        if(count <= 0){
            throw new IllegalArgumentException("음료는 1잔 이상 주문해야 합니다.");
        }
        for (int i = 0; i < count; i++) {
            beverages.add(beverage);

        }
        System.out.println(beverage.getName() + "이(가) "+count+"잔 추가 되었습니다.");
    }
    public void remove(Beverage beverage){
        beverages.remove(beverage);
        System.out.println(beverage.getName() + "이(가) 삭제 되었습니다.");
    }

    public List<Beverage> getBeverages() {
        return beverages;
    }

    public void clear(){
        beverages.clear();
    }

    public int calculateTotalPrice() {
        return beverages.stream().mapToInt(Beverage::getPrice).sum();
    }

    public Order createOrder(LocalTime currentTime) {
        if (currentTime.isBefore(SHOP_OPEN_TIME) || currentTime.isAfter(SHOP_CLOSE_TIME)) {
            throw new IllegalArgumentException("주문 시간이 아닙니다. 관리자에게 문의해주세요");
        }
        return new Order(LocalDateTime.now(), beverages);
    }
}
