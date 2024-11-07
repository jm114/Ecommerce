package com.spring.ecommerce.order.domain;

import com.spring.ecommerce.order.infra.entity.OrderEntity;
import com.spring.ecommerce.order.infra.entity.OrderItemEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
@Builder
public class Order {
    private Long id;
    private Long userId;
    private Map<Long, Integer> orderItem; //productId, quantity
    private LocalDateTime orderDt;



    public OrderEntity toEntity(){
        return OrderEntity.builder()
                .userId(this.userId)
                .orderDt(this.orderDt)
                .deleteYn("N")
                .build();
    }

    public OrderItemEntity toItemEntity(Long orderId, Long productId, int quantity, int price) {
        return OrderItemEntity.builder()
                .orderId(orderId)
                .productId(productId)
                .quantity(quantity)
                .price(price)
                .build();
    }
}
