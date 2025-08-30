package com.ordermanagement.orderservice.controller;

import com.ordermanagement.orderservice.dto.OrderRequest;
import com.ordermanagement.orderservice.entity.Order;
import com.ordermanagement.orderservice.repository.OrderRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ordermanagement")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @PostMapping("/placeorder")
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderRequest orderRequest) {
        try {
            Order order = new Order();
            order.setUserId(orderRequest.getUserId());
            order.setProduct(orderRequest.getProduct());
            order.setQuantity(orderRequest.getQuantity());
            order.setAmount(orderRequest.getAmount());
            order.setCreatedAt(LocalDateTime.now());
            orderRepository.save(order);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>("{'status':'0', 'message':'order placed successfully'}", HttpStatus.CREATED);
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
