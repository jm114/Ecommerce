package com.spring.ecommerce.order.application.service;

import com.spring.ecommerce.order.application.event.OrderCompleteEvent;
import com.spring.ecommerce.order.domain.Order;
import com.spring.ecommerce.order.domain.Product;
import com.spring.ecommerce.order.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ApplicationEventPublisher eventPublisher;

    //주문 정보 저장
    @Transactional
    public Order createOrder(Order order, Map<Long, Product> products) {
        Order completedOrder =  orderRepository.save(order, products);

        // 이벤트 발행(outbox 저장 -> kafka 메시지 발행)
        OrderCompleteEvent orderCompleteEvent = new OrderCompleteEvent(this, completedOrder);
        eventPublisher.publishEvent(orderCompleteEvent);

        return completedOrder;
    }

    public int calculateTotalPrice(Map<Long, Integer> orderItems, Map<Long, Product> products) {
        int totalPrice = 0;
        for (Map.Entry<Long, Integer> item : orderItems.entrySet()) {
            Long productId = item.getKey();
            Integer quantity = item.getValue();
            Product product = products.get(productId);
            if (product != null) {
                totalPrice += product.getPrice() * quantity;
            } else {
                throw new IllegalArgumentException("상품 정보가 없습니다: " + productId);
            }
        }
        return totalPrice;
    }



}