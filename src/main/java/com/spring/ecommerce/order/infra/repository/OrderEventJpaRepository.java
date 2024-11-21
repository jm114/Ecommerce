package com.spring.ecommerce.order.infra.repository;

import com.spring.ecommerce.order.domain.EventStatus;
import com.spring.ecommerce.order.infra.entity.OrderEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderEventJpaRepository extends JpaRepository<OrderEventEntity, Long> {
    OrderEventEntity save(OrderEventEntity orderEvent);

    OrderEventEntity findByOrderId(Long id);

    List<OrderEventEntity> findAllByStatus(EventStatus eventStatus);
}
