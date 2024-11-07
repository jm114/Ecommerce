package com.spring.ecommerce.order.infra.repository;

import com.spring.ecommerce.order.infra.entity.UserBalanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface UserBalanceJpaRepository extends JpaRepository<UserBalanceEntity, Long> {
    UserBalanceEntity findByUserId(Long userId);
}
