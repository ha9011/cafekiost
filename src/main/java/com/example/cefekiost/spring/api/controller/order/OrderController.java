package com.example.cefekiost.spring.api.controller.order;

import com.example.cefekiost.spring.api.service.order.OrderService;
import com.example.cefekiost.spring.api.service.order.request.OrderCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private OrderService orderService;

    @PostMapping("/api/v1/orders/new")
    public void createOrder(@RequestBody OrderCreateRequest request){
        orderService.createOrder(request);
    }
}
