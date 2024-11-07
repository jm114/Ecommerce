package com.spring.ecommerce.order.application.service;

import com.spring.ecommerce.order.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DataMessageService {

    @Async
    public void sendOrderData(Long userId, Order orders) {
        //TODO: 메세지 전송 상태, 로그 등 이력테이블에 남기기
    }
}
