package com.spring.ecommerce.order.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class Payment {
    private Long id;
    private Long orderId;
    private PaymentStatus  paymentStatus;
    private LocalDateTime paymentDt;
    private String deleteYn;

}
