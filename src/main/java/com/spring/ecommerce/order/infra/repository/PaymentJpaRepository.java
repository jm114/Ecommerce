package com.spring.ecommerce.order.infra.repository;

import com.spring.ecommerce.order.infra.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface PaymentJpaRepository extends JpaRepository<PaymentEntity, Long> {
}
