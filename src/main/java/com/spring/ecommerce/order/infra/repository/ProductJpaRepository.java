package com.spring.ecommerce.order.infra.repository;

import com.spring.ecommerce.order.domain.Product;
import com.spring.ecommerce.order.infra.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ProductJpaRepository extends JpaRepository<ProductEntity, Long> {
}
