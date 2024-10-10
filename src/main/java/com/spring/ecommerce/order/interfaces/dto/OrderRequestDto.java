package com.spring.ecommerce.order.interfaces.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderRequestDto {
    private Long userId;
    private Long productId;
    private Integer quantity;
    private Long price;
    private LocalDateTime orderDt;
}
