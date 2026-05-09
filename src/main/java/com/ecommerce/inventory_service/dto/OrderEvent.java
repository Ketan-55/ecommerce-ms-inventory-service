package com.ecommerce.inventory_service.dto;

import lombok.Data;

@Data
public class OrderEvent {

    private Long orderId;
    private Long userId;        // Add this
    private String productId;
    private Double totalPrice;  // Add this
    private Integer quantity;

}
