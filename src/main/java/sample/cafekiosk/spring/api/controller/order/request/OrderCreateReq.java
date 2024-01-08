package sample.cafekiosk.spring.api.controller.order.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 빈생성자가 있어야, @ReqBody 시, 데이터를 읽어올수있다.
public class OrderCreateReq {

    private List<String> productNumbers;
    @Builder
    private OrderCreateReq(List<String> productNumbers) {
        this.productNumbers = productNumbers;
    }
}
