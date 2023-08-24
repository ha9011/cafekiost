package com.example.cefekiost.spring.api.controller.order;

import com.example.cefekiost.spring.api.service.order.OrderService;
import com.example.cefekiost.spring.api.service.order.request.OrderCreateRequest;
import com.example.cefekiost.spring.api.service.order.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/api/v1/orders/new")
    public OrderResponse createOrder(@RequestBody OrderCreateRequest request){
        System.out.println("request : " + request.getProductNumbers());
        LocalDateTime registeredDateTime = LocalDateTime.now();
        OrderResponse order = orderService.createOrder(request, registeredDateTime);
        return order;
    }
}
