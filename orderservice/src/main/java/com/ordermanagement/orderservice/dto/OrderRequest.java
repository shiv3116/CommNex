package com.ordermanagement.orderservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.Date;

@Data
public class OrderRequest {

    @NotNull(message = "user id cannot be empty")
    private long userId;

    @NotBlank(message = "product is needed")
    private String product;

    @NotNull(message = "quantity cannot be null")
    private int quantity;

    @NotNull(message = "amount is needed")
    private double amount;
}
