package com.ordermanagement.notificationservice.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ordermanagement.notificationservice.client.UserClient;
import com.ordermanagement.notificationservice.dto.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsumer {

    @Autowired
    private UserClient userClient;

    @KafkaListener(topics = "order-confirmed", groupId = "notification-group" )
    public void consumeOrderConfirmed(String message) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(message);

        String userId = node.get("userId").asText();

        UserResponse user = userClient.getUserById(Long.parseLong(userId));

        System.out.println("Order confirmed notification sent to " + user.getEmail() + " for order id " + node.get("orderId").asText());
    }

    @KafkaListener(topics = "order-failed", groupId = "notification-group" )
    public void consumeOrderFailed(String message) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(message);

        String userId = node.get("userId").asText();
        String reason = node.get("reason").asText();

        UserResponse user = userClient.getUserById(Long.parseLong(userId));

        System.out.println("Order failed notification sent to " + user.getEmail() + " for order id " + node.get("orderId").asText());
        System.out.println("Order failure reason: " + reason);
    }
}
