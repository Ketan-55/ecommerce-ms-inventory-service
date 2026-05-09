package com.ecommerce.inventory_service.kafka;

import com.ecommerce.inventory_service.dto.InventoryEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void publishInventoryEvent(InventoryEvent event) {

        kafkaTemplate.send("inventory-topic", event);

        System.out.println("📤 Inventory event published");
    }
}
