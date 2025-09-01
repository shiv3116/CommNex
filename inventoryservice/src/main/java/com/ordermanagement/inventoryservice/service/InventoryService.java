package com.ordermanagement.inventoryservice.service;

import com.ordermanagement.inventoryservice.entity.Inventory;
import com.ordermanagement.inventoryservice.event.OrderConfirmedEvent;
import com.ordermanagement.inventoryservice.event.OrderFailedEvent;
import com.ordermanagement.inventoryservice.kafka.KafkaEventProducer;
import com.ordermanagement.inventoryservice.repository.InventoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private KafkaEventProducer kafkaEventProducer;

    @Transactional
    public void processOrder(String orderId, String userId, String product, String quantity) {
        int requiredQuantity = Integer.parseInt(quantity);
        Optional<Inventory> inventory = inventoryRepository.findByProduct(product);
        inventory.ifPresentOrElse( inv -> {
            int availableQuantity = inv.getAvailableQuantity();
            if(availableQuantity < requiredQuantity) {
                kafkaEventProducer.publishOrderFailed(new OrderFailedEvent(orderId, product, "product is out of stock", userId));
            } else {
                availableQuantity -= requiredQuantity;
                inv.setAvailableQuantity(availableQuantity);
                inventoryRepository.save(inv);
                kafkaEventProducer.publishOrderConfirmed(new OrderConfirmedEvent(orderId, product, userId));
            }
        }, () -> {
            kafkaEventProducer.publishOrderFailed(new OrderFailedEvent(orderId, product, "product not available in inventory", userId));
            throw new RuntimeException("product not available in inventory");
        });
    }
}
