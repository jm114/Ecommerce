package com.spring.ecommerce.order.domain.repository;

import com.spring.ecommerce.order.domain.Order;
import com.spring.ecommerce.order.domain.Product;
import com.spring.ecommerce.order.domain.User;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepository {

    public Order save(User user, Product product, Integer quantity) {

        return Order.builder()
                .id(product.getId())
                .userId(user.getId())
                .productId(product.getId())
                .price(product.getPrice())
                .quantity(quantity)
                .build();

    }
}
