package com.ecommerce.inventory_service.kafka;

import com.ecommerce.inventory_service.dto.InventoryEvent;
import com.ecommerce.inventory_service.dto.OrderEvent;
import com.ecommerce.inventory_service.entity.ProcessedEvent;
import com.ecommerce.inventory_service.repository.ProcessedEventRepository;
import com.ecommerce.inventory_service.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class KafkaConsumer {

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private ProcessedEventRepository processedEventRepository;

    @Autowired
    KafkaProducer inventoryProducer;

    @KafkaListener(topics = "order-topic", groupId = "inventory-group")
    public void consume(OrderEvent event){

        // 🔥 CHECK DUPLICATE EVENT
        boolean alreadyProcessed =
                processedEventRepository.existsByOrderId(event.getOrderId());

         InventoryEvent inventoryEvent = new InventoryEvent();
         inventoryEvent.setOrderId(event.getOrderId());
         inventoryEvent.setProductId(event.getProductId());
        if (alreadyProcessed) {

            System.out.println("⚠️ Duplicate event ignored: "
                    + event.getOrderId());

            return;
        }


        try {

            inventoryService.reduceStock(
                    event.getProductId(),
                    event.getQuantity()
            );

            ProcessedEvent processedEvent = new ProcessedEvent();

            processedEvent.setOrderId(event.getOrderId());
            processedEvent.setProcessedAt(LocalDateTime.now());

            processedEventRepository.save(processedEvent);

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
