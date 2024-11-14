package com.spring.ecommerce.order.application.event;

import com.spring.ecommerce.order.application.service.DataMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import static org.springframework.transaction.event.TransactionPhase.AFTER_COMMIT;

@Component
@RequiredArgsConstructor
public class OrderCompleteEventListener{

    private final DataMessageService messageService;

    @TransactionalEventListener(phase = AFTER_COMMIT)
    public void sendOrderData(OrderCompleteEvent event) {
        messageService.sendOrderData(event.getOrder());
    }
}
