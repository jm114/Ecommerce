package com.spring.ecommerce.order.interfaces.controller;

import com.spring.ecommerce.order.application.usecase.OrderServiceApplication;
import com.spring.ecommerce.order.domain.Order;
import com.spring.ecommerce.order.interfaces.dto.OrderRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderServiceApplication orderApplication;

    /**
     * 상품 주문/결제
     */
    @PostMapping("/checkout")
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequestDto request) {
        Order ordered = orderApplication.order(request.getUserId(), request.getProductId(), request.getQuantity());
        return ResponseEntity.ok(ordered);
    }



}
