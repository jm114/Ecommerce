package com.spring.ecommerce.order.infra.repository.impl;

import com.spring.ecommerce.order.domain.Order;
import com.spring.ecommerce.order.domain.Payment;
import com.spring.ecommerce.order.domain.PaymentStatus;
import com.spring.ecommerce.order.domain.repository.PaymentRepository;
import com.spring.ecommerce.order.infra.entity.PaymentEntity;
import com.spring.ecommerce.order.infra.repository.PaymentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
public class PaymentRepositoryImpl implements PaymentRepository {

    private final PaymentJpaRepository paymentJpaRepository;

    @Override
    public Payment updatePayment(Order order) {

        PaymentEntity paymentEntity = PaymentEntity.builder()
                .orderId(order.getId())
                .paymentStatus(PaymentStatus.PENDING)
                .paymentDt(LocalDateTime.now())
                .deleteYn("N")
                .build();

        PaymentEntity savedPayment = paymentJpaRepository.save(paymentEntity);
        return savedPayment.toDomain();

    }
}
