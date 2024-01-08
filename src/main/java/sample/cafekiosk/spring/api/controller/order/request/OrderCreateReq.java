package sample.cafekiosk.spring.api.controller.order.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderCreateReq {

    private List<String> productNumbers;
    @Builder
    private OrderCreateReq(List<String> productNumbers) {
        this.productNumbers = productNumbers;
    }
}
