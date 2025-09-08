package com.ordermanagement.inventoryservice.controller;

import com.ordermanagement.inventoryservice.dto.InventoryRequest;
import com.ordermanagement.inventoryservice.entity.Inventory;
import com.ordermanagement.inventoryservice.repository.InventoryRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryRepository inventoryRepository;




    @PostMapping("/addtoinventory")
    public ResponseEntity<?> addToInventory(@Valid @RequestBody InventoryRequest inventoryRequest) {
        try {
            Inventory inventory = new Inventory();
            inventory.setProduct(inventoryRequest.getProduct());
            inventory.setAvailableQuantity(inventoryRequest.getAvailableQuantity());
            inventoryRepository.save(inventory);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>("{'status':'0','message':'product added to inventory'}", HttpStatus.OK);
    }
}
