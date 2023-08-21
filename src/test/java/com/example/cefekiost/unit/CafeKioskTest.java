package com.example.cefekiost.unit;

import com.example.cefekiost.unit.beverage.Americano;
import com.example.cefekiost.unit.beverage.Latte;
import com.example.cefekiost.unit.order.Order;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class CafeKioskTest {

    @Test
    void add(){
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano(),1);

        System.out.println(">>> 담긴 음료 수 : " + cafeKiosk.getBeverages().size());
        System.out.println(">>> 담긴 음료 : " + cafeKiosk.getBeverages().get(0));

    }

    @Test
    public void add_auto() throws Exception{


        //given
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();


        //when
        cafeKiosk.add(americano,2);

        //then
        assertThat(cafeKiosk.getBeverages().get(0)).isEqualTo(americano);
        assertThat(cafeKiosk.getBeverages().get(1)).isEqualTo(americano);
    }

    @Test
    public void add_zero_auto() throws Exception{


        //given
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();



        //then
        assertThatThrownBy(() -> cafeKiosk.add(americano, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("음료는 1잔 이상 주문하실 수 있습니다");
    }

    @Test
    public void remove_auto() throws Exception{
        //given
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        Latte latte = new Latte();
        cafeKiosk.add(americano,2);
        cafeKiosk.add(latte,1);

        //when

        System.out.println(cafeKiosk.getBeverages().size());
        cafeKiosk.remove(americano);
        System.out.println(cafeKiosk.getBeverages().size());
        //then
        assertThat(cafeKiosk.getBeverages()).hasSize(1);
        assertThat(cafeKiosk.getBeverages().get(0)).isEqualTo(latte);
    }

    @Test
    public void clear_auto() throws Exception{
        //given
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        Latte latte = new Latte();
        cafeKiosk.add(americano,1);
        cafeKiosk.add(latte,1);


        //when
        cafeKiosk.allRemove();

        //then
        assertThat(cafeKiosk.getBeverages()).hasSize(0);
    }

    @Test
    public void createOrder_normal() throws Exception{
        //given
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        cafeKiosk.add(americano,1);

        //when

        LocalDateTime currentDateTime = LocalDateTime.of(2023, 7, 17, 10, 0);

        Order order = cafeKiosk.createOrder(currentDateTime);

        //then
        assertThat(order.getBeverages()).hasSize(1);
        assertThat(order.getBeverages().get(0)).isEqualTo(americano);

    }

    @Test
    public void createOrder_earlyopen() throws Exception{
        //given
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        cafeKiosk.add(americano,1);

        //when

        LocalDateTime currentDateTime = LocalDateTime.of(2023, 7, 17, 9, 59);

        //then
        assertThatThrownBy(() -> cafeKiosk.createOrder(currentDateTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주문 시간이 아닙니다. 관리자에게 문의하세요");

    }



    @Test
    public void calculateTotalPrice() throws Exception{
        //given
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        Latte latte = new Latte();

        //when
        cafeKiosk.add(americano,1);
        cafeKiosk.add(latte,1);

        int totalPrice = cafeKiosk.calculateTotalPrice();
        //then
        assertThat(totalPrice).isEqualTo(8500);
    }
}