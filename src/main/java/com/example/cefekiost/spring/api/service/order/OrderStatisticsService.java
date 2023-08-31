package com.example.cefekiost.spring.api.service.order;

import com.example.cefekiost.spring.api.service.mail.MailService;
import com.example.cefekiost.spring.domain.order.Order;
import com.example.cefekiost.spring.domain.order.OrderRepository;
import com.example.cefekiost.spring.domain.order.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderStatisticsService {

    private final OrderRepository orderRepository;

    private final MailService mailService;

    public boolean sendOrderStatisticsMail(LocalDate orderDate, String email){
        // 해당 일자에 결제완료된 주문들을 가져와서  TODO 테스트 해보기
        List<Order> orders = orderRepository.findOrdersBy(
            orderDate.atStartOfDay(), //atStartOfDay 0시
            orderDate.plusDays(1).atStartOfDay(), // 1일 더해서 0시
            OrderStatus.PAYMENT_COMPLETED
        );

        // 총 매출 합계를 계산하고
        int totalAmount = orders.stream().mapToInt(Order::getTotalPrice).sum();

        // 메일전송
        boolean result = mailService.sendMail(
                "no-reply@cafekiosk.com",
                email,
                String.format("[매출통계] %s", orderDate),
                String.format("총 매출 합계는 %s원 입니다.", totalAmount)
        );

        if(!result){
            throw new IllegalArgumentException("매출 통계 메일 전송에 실패했습니다.");
        }
        return true;
    }
}
