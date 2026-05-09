package com.ecommerce.inventory_service.service;

import com.ecommerce.inventory_service.entity.Inventory;
import com.ecommerce.inventory_service.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    public void reduceStock(String productId, Integer quantity){

        Inventory inventory = inventoryRepository.findById(productId).orElse(null);

        if(inventory ==null){
            throw  new RuntimeException("Inventory not found");
        }

        if(inventory.getStock()<quantity){
            throw new RuntimeException("Insufficient stock");
        }

        inventory.setStock(inventory.getStock()-quantity);
        inventory.setUpdatedAt(LocalDateTime.now());

        inventoryRepository.save(inventory);
        System.out.println("Inventory updated" +productId);

    }
}

