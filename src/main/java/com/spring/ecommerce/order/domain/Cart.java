package com.spring.ecommerce.order.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Cart {
    private Long id;
    private Long userId;
    private Long productId;
    private Integer quantity;
    private String deleteYn;
}
