package com.ordermanagement.orderservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "userid", nullable = false, updatable = false)
    private long userId;

    @Column(name = "product", nullable = false, updatable = false, length = 255)
    private String product;

    @Column(name = "quantity", nullable = false, updatable = false)
    private int quantity;

    @Column(name = "amount", nullable = false, updatable = false)
    private double amount;

    @Column(name = "createdate", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
