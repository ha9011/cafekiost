package sample.cafekiosk.unit.order;

import sample.cafekiosk.unit.Beverage;

import java.time.LocalDateTime;
import java.util.List;

public class Order {

    private LocalDateTime localDateTime;
    private List<Beverage> beverages;

    public Order(LocalDateTime localDateTime, List<Beverage> beverages) {
        this.localDateTime = localDateTime;
        this.beverages = beverages;
    }
}
