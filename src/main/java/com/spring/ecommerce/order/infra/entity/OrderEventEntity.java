package com.spring.ecommerce.order.infra.entity;

import com.spring.ecommerce.order.domain.EventStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "order_event")
public class OrderEventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId; // 관련 주문 ID

    @Enumerated(EnumType.STRING)
    private EventStatus status; // PENDING or SENT

    @Lob
    private String payload; // Kafka 메시지 데이터

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public OrderEventEntity(Long orderId, EventStatus status) {
        this.orderId = orderId;
        this.status = status;
        this.payload = generatePayload(orderId);
        this.createdAt = LocalDateTime.now();
    }

    public void markAsPublished() {
        this.status = EventStatus.SENT;
        this.updatedAt = LocalDateTime.now();
    }

    private String generatePayload(Long orderId) {
        return "{ \"orderId\": " + orderId + ", \"status\": \"ORDER_COMPLETE\" }";
    }
}
