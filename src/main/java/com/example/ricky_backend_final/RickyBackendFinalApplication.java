package com.example.ricky_backend_final;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RickyBackendFinalApplication {

    public static void main(String[] args) {
        SpringApplication.run(RickyBackendFinalApplication.class, args);

        System.out.println("🚗 Ricky Backend Server Started Successfully!");
        System.out.println("📡 Server running on: http://localhost:8080");
        System.out.println("📋 API Base URL: http://localhost:8080/api");
    }
}
