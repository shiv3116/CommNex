package com.ordermanagement.inventoryservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "inventory")
public class Inventory {

    @Id
    @Column(name = "product", nullable = false, unique = true)
    private String product;

    @Column(name = "availablequantity", nullable = false, updatable = true)
    private int availableQuantity;
}
