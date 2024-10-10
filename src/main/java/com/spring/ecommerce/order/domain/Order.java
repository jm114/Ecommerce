package com.spring.ecommerce.order.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
@Builder
public class Order {
    private Long id;
    private Long userId;
    private Long productId;
    private LocalDateTime orderDt;
    private Integer quantity;
    private Long price;

    //private Map<Long, orderItem> orderItems;

    /*
    static class orderItem{
        private Long id;
        private Long orderId;
        private Long productId;
        private Integer quantity;
        private Long price;
    }
    */
}
