package com.spring.ecommerce.order.infra.kafka;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaController {
    private final KafkaMessageProducer producer;

    public KafkaController(KafkaMessageProducer producer) {
        this.producer = producer;
    }

    @GetMapping("/send")
    public String sendMessage() {
        producer.sendMessage("Bye Kafka!");
        return "Message sent!";
    }
}
