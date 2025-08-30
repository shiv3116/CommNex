package com.ordermanagement.orderservice.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreated {
    private long orderId;
    private long userId;
    private String product;
    private int quantity;
    private double amount;
}
