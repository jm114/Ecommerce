package com.spring.ecommerce.order.domain.repository;

import com.spring.ecommerce.order.domain.Order;
import com.spring.ecommerce.order.domain.Product;

import java.util.Map;

public interface OrderRepository {
    Order save(Order order, Map<Long, Product> products);

    Order findById(Long orderId);
}
