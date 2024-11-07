package com.spring.ecommerce.order.domain.repository;

import com.spring.ecommerce.order.domain.Order;
import com.spring.ecommerce.order.domain.Payment;
import org.springframework.stereotype.Repository;

public interface PaymentRepository {
    Payment updatePayment(Order order);
}
