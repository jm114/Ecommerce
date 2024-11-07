package com.spring.ecommerce.order.infra.repository;

import com.spring.ecommerce.order.domain.User;
import com.spring.ecommerce.order.infra.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
}
