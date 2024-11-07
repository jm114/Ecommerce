package com.spring.ecommerce.order.interfaces.dto;

import com.spring.ecommerce.order.domain.Order;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class OrderRequest {
    private Long userId;
    private List<OrderItemRequest> orderItems;
    private LocalDateTime orderDt;

    public Order convertToOrder() {
        Map<Long, Integer> items = new HashMap<>();

        for (OrderItemRequest item : this.orderItems) {
            items.put(item.getProductId(), item.getQuantity());
        }

        return Order.builder()
                .userId(this.userId)
                .orderItem(items)
                .orderDt(LocalDateTime.now())
                .build();
    }
}
