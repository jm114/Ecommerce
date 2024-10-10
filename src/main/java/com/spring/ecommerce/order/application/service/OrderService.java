package com.spring.ecommerce.order.application.service;

import com.spring.ecommerce.order.domain.Order;
import com.spring.ecommerce.order.domain.Product;
import com.spring.ecommerce.order.domain.User;
import com.spring.ecommerce.order.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    //주문 정보 저장
    public Order createOrder(User user, Product product, Integer quantity) {
        return orderRepository.save(user, product, quantity);
    }
}