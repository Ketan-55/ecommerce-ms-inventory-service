package com.ecommerce.inventory_service.kafka;

import com.ecommerce.inventory_service.dto.InventoryEvent;
import com.ecommerce.inventory_service.dto.OrderEvent;
import com.ecommerce.inventory_service.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    KafkaProducer inventoryProducer;

    @KafkaListener(topics = "order-topic", groupId = "inventory-group")
    public void consume(OrderEvent event){

        System.out.println("📦 Received order event for product: "
                + event.getProductId());

        InventoryEvent inventoryEvent = new InventoryEvent();

        inventoryEvent.setOrderId(event.getOrderId());
        inventoryEvent.setProductId(event.getProductId());

        try {

            inventoryService.reduceStock(
                    event.getProductId(),
                    event.getQuantity()
            );

            inventoryEvent.setStatus("SUCCESS");
            inventoryEvent.setMessage("Stock updated successfully");

            System.out.println("✅ Inventory updated");

        } catch (Exception e) {

            inventoryEvent.setStatus("FAILED");
            inventoryEvent.setMessage(e.getMessage());

            System.out.println("❌ Inventory update failed");
        }

        inventoryProducer.publishInventoryEvent(inventoryEvent);
    }


}
