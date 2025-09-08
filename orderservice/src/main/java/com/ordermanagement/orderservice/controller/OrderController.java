package com.ordermanagement.orderservice.controller;

import com.ordermanagement.orderservice.dto.OrderRequest;
import com.ordermanagement.orderservice.entity.Order;
import com.ordermanagement.orderservice.events.OrderCreated;
import com.ordermanagement.orderservice.kafka.KafkaOrderProducer;
import com.ordermanagement.orderservice.repository.OrderRepository;
import com.ordermanagement.orderservice.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private KafkaOrderProducer kafkaOrderProducer;

    @Autowired
    private OrderService orderService;

    @PostMapping("/placeorder")
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderRequest orderRequest) {
        String res;
        try {
            res = orderService.placeOrder(orderRequest);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @GetMapping("/order/{userId}")
    public ResponseEntity<?> getOrdersByUserId(@PathVariable long userId) {
        List<Optional<Order>> orders;
        try {
            orders = orderRepository.findByUserId(userId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @DeleteMapping("/deleteorder/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable long id) {
        try {
            Optional<Order> order = orderRepository.findById(id);
            order.ifPresentOrElse(o -> {
                orderRepository.delete(o);
            }, () -> {
                throw new RuntimeException("order not found");
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>("{'status':'0','message':'order deleted successfully'}", HttpStatus.OK);
    }
}
