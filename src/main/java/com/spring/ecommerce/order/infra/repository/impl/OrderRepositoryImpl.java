package com.spring.ecommerce.order.infra.repository.impl;

import com.spring.ecommerce.order.domain.Order;
import com.spring.ecommerce.order.domain.Product;
import com.spring.ecommerce.order.domain.repository.OrderRepository;
import com.spring.ecommerce.order.infra.entity.OrderEntity;
import com.spring.ecommerce.order.infra.entity.OrderItemEntity;
import com.spring.ecommerce.order.infra.repository.OrderItemJpaRepository;
import com.spring.ecommerce.order.infra.repository.OrderJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderJpaRepository orderJpaRepository;
    private final OrderItemJpaRepository orderItemJpaRepository;

    @Override
    public Order save(Order order, Map<Long, Product> products) {
        OrderEntity savedOrder = orderJpaRepository.save(order.toEntity());

        List<OrderItemEntity> savedOrderItems = new ArrayList<>();

        for (Map.Entry<Long, Integer> entry : order.getOrderItem().entrySet()) {
            Long productId = entry.getKey();
            int quantity = entry.getValue();

            Product product = products.get(productId);
            int price = product.getPrice();

            OrderItemEntity orderItemEntity = order.toItemEntity(savedOrder.getId(), productId, quantity, price);
            orderItemJpaRepository.save(orderItemEntity);

            savedOrderItems.add(orderItemEntity);
        }

        return Order.builder()
                .id(savedOrder.getId())
                .userId(savedOrder.getUserId())
                .orderDt(savedOrder.getOrderDt())
                .orderItem(convertToOrderItemMap(savedOrderItems))
                .build();
    }

    @Override
    public Order findById(Long orderId) {
        Optional<OrderEntity> orderEntityOptional = orderJpaRepository.findById(orderId);
        if (orderEntityOptional.isEmpty()) {
            throw new EntityNotFoundException("Order not found with id: " + orderId);
        }

        OrderEntity orderEntity = orderEntityOptional.get();

        List<OrderItemEntity> orderItemEntities = orderItemJpaRepository.findByOrderId(orderId);

        Map<Long, Integer> orderItems = new HashMap<>();
        for (OrderItemEntity orderItemEntity : orderItemEntities) {
            orderItems.put(orderItemEntity.getProductId(), orderItemEntity.getQuantity());
        }

        return Order.builder()
                .id(orderEntity.getId())
                .userId(orderEntity.getUserId())
                .orderDt(orderEntity.getOrderDt())
                .orderItem(orderItems)
                .build();
    }

    private Map<Long, Integer> convertToOrderItemMap(List<OrderItemEntity> orderItemEntities) {
        Map<Long, Integer> orderItemMap = new HashMap<>();
        for (OrderItemEntity entity : orderItemEntities) {
            orderItemMap.put(entity.getProductId(), entity.getQuantity());
        }
        return orderItemMap;
    }

}
