package com.ordermanagement.orderservice.kafka;

import com.ordermanagement.orderservice.events.OrderCreated;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaOrderProducer {

    private final KafkaTemplate<String, OrderCreated> kafkaTemplate;

    public KafkaOrderProducer(KafkaTemplate<String, OrderCreated> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrderCreatedEvent(OrderCreated orderCreated) {
        kafkaTemplate.send("order-created", String.valueOf(orderCreated.getOrderId()), orderCreated);
    }
}
