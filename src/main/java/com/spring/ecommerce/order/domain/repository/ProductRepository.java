package com.spring.ecommerce.order.domain.repository;

import com.spring.ecommerce.order.domain.Product;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public interface ProductRepository{

    List<Product> findAll();

    Product findById(Long productId);

    Product updateProduct(Product productInfo);

    List<Product> findTopSelling(LocalDateTime startDate, LocalDateTime endDate, int limit);
}
