package com.spring.ecommerce.order.application.service;

import com.spring.ecommerce.order.domain.Order;
import com.spring.ecommerce.order.domain.Payment;
import com.spring.ecommerce.order.domain.Product;
import com.spring.ecommerce.order.domain.User;
import com.spring.ecommerce.order.domain.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Transactional
    public Payment executePayment(Order order) {
        return paymentRepository.updatePayment(order);
    }
}
