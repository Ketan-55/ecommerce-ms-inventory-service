package com.ecommerce.inventory_service.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorResponse {

    private String message;

    private Integer status;

    private LocalDateTime timestamp;

}
