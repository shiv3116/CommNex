package com.ordermanagement.inventoryservice.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderFailedEvent {
    private String orderId;
    private String product;
    private String reason;
    private String userId;
}
