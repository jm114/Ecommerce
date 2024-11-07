package com.spring.ecommerce.order.interfaces.dto;

import lombok.Data;

@Data
public class UserRequest {
    private Long userId;
    private int amount;
}
