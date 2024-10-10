package com.spring.ecommerce.order.infra.repository;

import com.spring.ecommerce.order.infra.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpaRepository extends JpaRepository<OrderEntity, Long> {
}
