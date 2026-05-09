package com.ecommerce.inventory_service.dto;

import lombok.Data;

@Data
public class InventoryEvent {

    private Long orderId;

    private String productId;

    private String status;

    private String message;
}
