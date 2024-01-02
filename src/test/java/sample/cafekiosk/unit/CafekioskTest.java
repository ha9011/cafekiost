package sample.cafekiosk.unit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class CafekioskTest {

    @Test
    @DisplayName("음료를 추가한다.")
    void addAmericano() {
        // given
        Cafekiosk cafekiosk = new Cafekiosk();

        // when
        Americano americano = new Americano();
        cafekiosk.add(americano);

        // then
        assertThat(cafekiosk.getBeverages().get(0).getName()).isEqualTo(americano.getName());
        assertThat(cafekiosk.getBeverages()).hasSize(1);
    }

    @Test
    @DisplayName("아메리카노를 지운다")
    void removeAmericano() {
        // given
        Cafekiosk cafekiosk = new Cafekiosk();

        // when
        Americano americano = new Americano();
        cafekiosk.add(americano);
        cafekiosk.remove(americano);
        // then
        assertThat(cafekiosk.getBeverages()).hasSize(0);
        assertThat(cafekiosk.getBeverages()).isEmpty();
    }

    @Test
    @DisplayName("아메리카노를 지운다")
    void clearBeverage() {
        // given
        Cafekiosk cafekiosk = new Cafekiosk();

        // when
        Americano americano = new Americano();
        Latte latte = new Latte();
        cafekiosk.add(americano);
        cafekiosk.add(latte);
        cafekiosk.clear();
        // then
        assertThat(cafekiosk.getBeverages()).hasSize(0);
        assertThat(cafekiosk.getBeverages()).isEmpty();
    }

    @Test
    @DisplayName("아메리카노 2잔 추가")
    void addTwoAmericanos() {
        // given
        Cafekiosk cafekiosk = new Cafekiosk();
        Americano americano = new Americano();

        // when
        cafekiosk.add(americano, 2);

        // then
        assertThat(cafekiosk.getBeverages().get(0)).isEqualTo(americano);
        assertThat(cafekiosk.getBeverages().get(1)).isEqualTo(americano);
        assertThat(cafekiosk.getBeverages()).hasSize(2);

    }

    @Test
    @DisplayName("아메리카노 0잔 추가")
    void addZeroAmericano() {
        // given
        Cafekiosk cafekiosk = new Cafekiosk();
        Americano americano = new Americano();


        // then
        assertThatThrownBy(() -> cafekiosk.add(americano, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("음료는 1잔 이상 주문해야 합니다.");
    }

    @Test
    @DisplayName("영업시간 이외의 시간에서 주문할 경우")
    void createOrder() {
        // given
        Cafekiosk cafekiosk = new Cafekiosk();
        Americano americano = new Americano();

        // when
        cafekiosk.add(americano);

        // then


        assertThatThrownBy(()->cafekiosk.createOrder(LocalTime.of(9, 59)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주문 시간이 아닙니다. 관리자에게 문의해주세요");
    }

}