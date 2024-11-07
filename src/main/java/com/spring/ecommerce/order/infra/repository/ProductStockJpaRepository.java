package com.spring.ecommerce.order.infra.repository;

import com.spring.ecommerce.order.infra.entity.ProductStockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ProductStockJpaRepository extends JpaRepository<ProductStockEntity, Long> {
    List<ProductStockEntity> findByProductIdIn(List<Long> productIds);
}
