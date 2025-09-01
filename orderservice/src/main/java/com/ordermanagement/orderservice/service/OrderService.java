package com.ordermanagement.orderservice.service;

import com.ordermanagement.orderservice.dto.OrderRequest;
import com.ordermanagement.orderservice.entity.Order;
import com.ordermanagement.orderservice.events.OrderCreated;
import com.ordermanagement.orderservice.kafka.KafkaOrderProducer;
import com.ordermanagement.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private KafkaOrderProducer kafkaOrderProducer;

    private ConcurrentHashMap<Long, CompletableFuture<String>> orderFutures = new ConcurrentHashMap<>();

    public String placeOrder(OrderRequest orderRequest) {
        long orderId = new Random().nextLong();
        CompletableFuture<String> future = new CompletableFuture<String>();
        orderFutures.put(orderId, future);
        try {
            OrderCreated orderCreated = new OrderCreated(orderId, orderRequest.getUserId(), orderRequest.getProduct(), orderRequest.getQuantity(), orderRequest.getAmount());
            kafkaOrderProducer.sendOrderCreatedEvent(orderCreated);
        } catch (Exception e) {
            orderFutures.remove(orderId);
            throw new RuntimeException(e);
        }
        String result;
        try {
            result = future.get(5, TimeUnit.SECONDS);
        } catch (Exception e) {
            return "{\"status\":\"TIMEOUT\",\"message\":\"Inventory did not respond\"}";
        }
        if("confirmed".equals(result)) {
            Order order = new Order();
            order.setUserId(orderRequest.getUserId());
            order.setProduct(orderRequest.getProduct());
            order.setQuantity(orderRequest.getQuantity());
            order.setAmount(orderRequest.getAmount());
            order.setCreatedAt(LocalDateTime.now());
            orderRepository.save(order);
            return "{\"status\":\"CONFIRMED\",\"message\":\"Order placed successfully\"}";
        }
        return "{\"status\":\"FAILED\",\"message\":\"Order placement failed\"}";
    }

    public void orderConfirmed(String orderId) {
        CompletableFuture<String> future = orderFutures.remove(Long.parseLong(orderId));
        if(future != null) {
            future.complete("confirmed");
        }
    }

    public void orderFailed(String orderId) {
        CompletableFuture<String> future = orderFutures.remove(Long.parseLong(orderId));
        if(future != null) {
            future.complete("failed");
        }
    }
}
