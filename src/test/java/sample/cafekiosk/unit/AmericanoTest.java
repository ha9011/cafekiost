package sample.cafekiosk.unit;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AmericanoTest {

    @Test
    @DisplayName("아메리카노 이름")
    void getName() {
        // given
        Americano americano = new Americano();
        // when

        // then
        assertThat(americano.getName()).isEqualTo("아메리카노");

    }


    @Test
    @DisplayName("아메리카노 가격")
    void getPrice() {
        // given
        Americano americano = new Americano();

        // when

        // then
        assertThat(americano.getPrice()).isEqualTo(4000);
    }
}