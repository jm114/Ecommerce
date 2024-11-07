package com.spring.ecommerce.order.infra.repository.impl;

import com.spring.ecommerce.order.domain.Order;
import com.spring.ecommerce.order.domain.Product;
import com.spring.ecommerce.order.domain.repository.OrderRepository;
import com.spring.ecommerce.order.infra.entity.OrderEntity;
import com.spring.ecommerce.order.infra.entity.OrderItemEntity;
import com.spring.ecommerce.order.infra.repository.OrderItemJpaRepository;
import com.spring.ecommerce.order.infra.repository.OrderJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderJpaRepository orderJpaRepository;
    private final OrderItemJpaRepository orderItemJpaRepository;

    @Override
    public Order save(Order order, Map<Long, Product> products) {
        OrderEntity savedOrder = orderJpaRepository.save(order.toEntity());

        for (Map.Entry<Long, Integer> entry : order.getOrderItem().entrySet()) {
            Long productId = entry.getKey();
            int quantity = entry.getValue();

            Product product = products.get(productId);
            int price = product.getPrice();

            OrderItemEntity orderItemEntity = order.toItemEntity(savedOrder.getId(), productId, quantity, price);
            orderItemJpaRepository.save(orderItemEntity);
        }
        return order;
    }

}
