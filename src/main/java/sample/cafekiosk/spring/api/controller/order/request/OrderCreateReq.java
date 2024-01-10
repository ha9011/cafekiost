package sample.cafekiosk.spring.api.controller.order.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 빈생성자가 있어야, @ReqBody 시, 데이터를 읽어올수있다.
public class OrderCreateReq {

    @NotEmpty(message = "상품번호 리스트는 필수입니다.")
    private List<String> productNumbers;
    @Builder
    private OrderCreateReq(List<String> productNumbers) {
        this.productNumbers = productNumbers;
    }
}
