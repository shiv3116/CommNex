package com.ordermanagement.orderservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ordermanagement.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaOrderConsumer {

    @Autowired
    private OrderService orderService;

    @KafkaListener(topics = "order-confirmed", groupId = "order-group")
    public void consumeOrderConfirmed(String message) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(message);
        String orderId = node.get("orderId").asText();
        orderService.orderConfirmed(orderId);
    }

    @KafkaListener(topics = "order-failed", groupId = "order-group")
    public void consumeOrderFailed(String message) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(message);
        String orderId = node.get("orderId").asText();
        orderService.orderFailed(orderId);
    }
}
