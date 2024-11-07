package com.spring.ecommerce.order.infra.entity;

import com.spring.ecommerce.order.domain.Payment;
import com.spring.ecommerce.order.domain.PaymentStatus;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "payment")
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    private LocalDateTime paymentDt;

    private String deleteYn;

    public Payment toDomain(){
        return Payment.builder()
                .id(this.id)
                .orderId(this.id)
                .paymentStatus(this.paymentStatus)
                .paymentDt(this.paymentDt)
                .deleteYn(this.deleteYn)
                .build();
    }
}
