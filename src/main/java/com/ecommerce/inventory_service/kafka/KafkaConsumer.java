package com.ecommerce.inventory_service.kafka;

import com.ecommerce.inventory_service.dto.OrderEvent;
import com.ecommerce.inventory_service.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    @Autowired
    private InventoryService inventoryService;

    @KafkaListener(topics = "order-topic", groupId = "inventory-group")
    public void consume(OrderEvent event){

        System.out.println("🔥 Received order event: " + event.getOrderId());

        try {
            inventoryService.reduceStock(event.getProductId(), event.getQuantity());
            System.out.println("✅ Stock reduced for product: " + event.getProductId());
        } catch (RuntimeException e) {
            System.err.println("❌ Failed to reduce stock for product: " + event.getProductId() + " - " + e.getMessage());
        }

    }


}
