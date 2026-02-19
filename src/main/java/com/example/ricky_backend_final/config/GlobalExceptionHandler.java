package com.example.ricky_backend_final.config;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleDataIntegrityViolation(
            DataIntegrityViolationException ex, WebRequest request) {
    
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Data Integrity Violation");
        response.put("path", request.getDescription(false).replace("uri=", ""));
    
        // 🔥 REAL ROOT CAUSE (Postgres message)
        String rootMessage = ex.getMostSpecificCause() != null
                ? ex.getMostSpecificCause().getMessage()
                : ex.getMessage();
    
        response.put("message", rootMessage);
    
        // 🔍 Optional: classify by SQLState if present
        if (rootMessage != null) {
            if (rootMessage.contains("23505")) {
                response.put("type", "UNIQUE_VIOLATION");
            } else if (rootMessage.contains("23502")) {
                response.put("type", "NOT_NULL_VIOLATION");
            } else if (rootMessage.contains("23503")) {
                response.put("type", "FOREIGN_KEY_VIOLATION");
            }
        }
    
        // 🔥 LOG FULL DETAILS (DO NOT GUESS)
        System.err.println("=== DATA INTEGRITY VIOLATION ===");
        ex.printStackTrace();
    
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }


    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneralException(
            Exception ex, WebRequest request) {

        // 🔥 Do NOT handle WebSocket upgrade exceptions
        if (request.getDescription(false).contains("/ws-device")) {
            throw new RuntimeException(ex);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.put("error", "Internal Server Error");
        response.put("message", ex.getMessage());
        response.put("path", request.getDescription(false).replace("uri=", ""));

        ex.printStackTrace();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}

