package com.ecommerce.inventory_service.exception;

import com.ecommerce.inventory_service.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InventoryNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleInventoryNotFound(
            InventoryNotFoundException ex) {

        ErrorResponse response = new ErrorResponse();

        response.setMessage(ex.getMessage());
        response.setStatus(404);
        response.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(
                response,
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientStock(
            InsufficientStockException ex) {

        ErrorResponse response = new ErrorResponse();

        response.setMessage(ex.getMessage());
        response.setStatus(400);
        response.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(
                response,
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex) {

        ErrorResponse response = new ErrorResponse();

        response.setMessage(ex.getMessage());
        response.setStatus(500);
        response.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(
                response,
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}