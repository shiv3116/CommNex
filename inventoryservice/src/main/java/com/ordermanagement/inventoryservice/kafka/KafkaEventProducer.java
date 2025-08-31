package com.ordermanagement.inventoryservice.kafka;

import com.ordermanagement.inventoryservice.event.OrderConfirmedEvent;
import com.ordermanagement.inventoryservice.event.OrderFailedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishOrderConfirmed(OrderConfirmedEvent orderConfirmedEvent) {
        kafkaTemplate.send("order-confirmed", orderConfirmedEvent);
    }

    public void publishOrderFailed(OrderFailedEvent orderFailedEvent) {
        kafkaTemplate.send("order-failed", orderFailedEvent);
    }
}
