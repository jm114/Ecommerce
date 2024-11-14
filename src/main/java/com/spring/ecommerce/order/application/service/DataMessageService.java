package com.spring.ecommerce.order.application.service;

import com.spring.ecommerce.order.domain.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class DataMessageService {

    @Async
    public void sendOrderData(Order orders) {
        //TODO: 메세지 전송 상태, 로그 등 이력테이블에 남기기

        log.info("성공한 주문은? : {}", orders);
    }
}
