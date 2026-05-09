package com.ecommerce.inventory_service.repository;

import com.ecommerce.inventory_service.entity.ProcessedEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessedEventRepository extends JpaRepository<ProcessedEvent, String> {
    boolean existsByOrderId(Long orderId);
}
