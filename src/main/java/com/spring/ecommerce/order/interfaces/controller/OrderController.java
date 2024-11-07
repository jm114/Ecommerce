package com.spring.ecommerce.order.interfaces.controller;

import com.spring.ecommerce.order.application.usecase.OrderFacade;
import com.spring.ecommerce.order.domain.Order;
import com.spring.ecommerce.order.interfaces.dto.OrderRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders")
@Tag(name = "Order", description = "상품 주문 및 결제 관련 API")
public class OrderController {

    private final OrderFacade orderFacade;

    /**
     * 상품 주문/결제
     */
    @PostMapping("/checkout")
    @Operation(summary = "상품 주문", description = "상품을 주문하고 결제합니다.")
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest request) throws InterruptedException {

        Order ordered = orderFacade.order(request);
        if(ordered == null) throw new IllegalArgumentException("주문이 실패하였습니다");

        return ResponseEntity.ok(ordered);
    }



}
