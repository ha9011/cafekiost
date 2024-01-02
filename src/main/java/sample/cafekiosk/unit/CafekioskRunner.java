package sample.cafekiosk.unit;

public class CafekioskRunner {
    public static void main(String[] args) {
        Cafekiosk cafekiosk = new Cafekiosk();
        cafekiosk.add(new Americano());
        cafekiosk.add(new Latte());

        int totalPrice = cafekiosk.calculateTotalPrice();
        System.out.println(totalPrice);
    }
}
