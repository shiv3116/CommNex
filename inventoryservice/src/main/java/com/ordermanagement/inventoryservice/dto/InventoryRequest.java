package com.ordermanagement.inventoryservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class InventoryRequest {

    @NotBlank(message = "product cannot be empty")
    private String product;

    @NotNull(message = "quantity cannot be null")
    @Size(min = 1, message = "quantity must be at least 1")
    private int availableQuantity;
}
