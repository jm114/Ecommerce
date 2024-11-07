package com.spring.ecommerce.order.interfaces.dto;

import lombok.Getter;

@Getter
public class OrderItemRequest {
    private Long productId;
    private int quantity;
}
