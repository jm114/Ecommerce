package com.spring.ecommerce.order.application.event;

import com.spring.ecommerce.order.domain.EventStatus;
import com.spring.ecommerce.order.domain.Order;
import com.spring.ecommerce.order.domain.repository.OrderRepository;
import com.spring.ecommerce.order.infra.entity.OrderEventEntity;
import com.spring.ecommerce.order.infra.repository.OrderEventJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;


@Component
@RequiredArgsConstructor
public class OrderCompleteEventListener{

    private final OrderEventJpaRepository orderEventRepository;
    private final OrderRepository orderRepository;
    private final KafkaTemplate<String, Order> kafkaTemplate;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void saveToOutbox(OrderCompleteEvent event) {
        OrderEventEntity orderEvent = new OrderEventEntity(event.getOrder().getId(), EventStatus.PENDING);
        orderEventRepository.save(orderEvent);
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void sendOrderData(OrderCompleteEvent event) {
        OrderEventEntity orderEvent = orderEventRepository.findByOrderId(event.getOrder().getId());

        // Kafka 메시지 발행
        kafkaTemplate.send("ORDER_COMPLETE", event.getOrder())
            .thenAccept(result -> {
                orderEvent.markAsPublished();
                orderEventRepository.save(orderEvent);
                System.out.println("Kafka 메시지 발행 성공: " + result.getRecordMetadata());
            })
            .exceptionally(ex -> {
                System.err.println("Kafka 발행 실패: " + ex.getMessage());
                return null;
            });
    }


    //처리가 안된 메시지들 재발행 시도(5초마다)
    @Scheduled(fixedRate = 5000)
    public void processPendingEvents() {
        List<OrderEventEntity> pendingEvents = orderEventRepository.findAllByStatus(EventStatus.PENDING);

        for (OrderEventEntity event : pendingEvents) {
            Order orderInfo = orderRepository.findById(event.getOrderId());

            try {
                kafkaTemplate.send("ORDER_COMPLETE", orderInfo);
                event.markAsPublished();
                orderEventRepository.save(event);
            } catch (Exception e) {
                System.err.println("Kafka 발행 실패 (재시도 중): " + event.getId());
            }
        }
    }


}
