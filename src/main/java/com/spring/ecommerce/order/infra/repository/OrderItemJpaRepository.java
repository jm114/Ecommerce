package com.spring.ecommerce.order.infra.repository;

import com.spring.ecommerce.order.infra.entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface OrderItemJpaRepository extends JpaRepository<OrderItemEntity, Long> {

}
