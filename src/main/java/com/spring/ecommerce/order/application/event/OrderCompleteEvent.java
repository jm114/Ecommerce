package com.spring.ecommerce.order.application.event;

import com.spring.ecommerce.order.domain.Order;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class OrderCompleteEvent extends ApplicationEvent {
//spring 4.2 이전은 ApplicationEvent를 확장해야한다고함..

    private final Order order;

    public OrderCompleteEvent(Object source, Order order) {
        super(source);
        this.order = order;
    }
}
