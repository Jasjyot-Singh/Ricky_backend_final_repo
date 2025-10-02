package com.example.ricky_backend_final;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@SpringBootApplication
@EnableWebSocketMessageBroker
public class RickyBackendFinalApplication implements WebSocketMessageBrokerConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(RickyBackendFinalApplication.class, args);
        System.out.println("🚗 Ricky Backend Server Started Successfully!");
        System.out.println("📡 Server running on: http://localhost:8080");
        System.out.println("🌐 WebSocket enabled for real-time updates");
        System.out.println("📋 API Base URL: http://localhost:8080/api");
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Enable simple broker for topics (SOS alerts, fare updates, etc.)
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // WebSocket endpoint for SOS alerts and real-time notifications
        registry.addEndpoint("/ws-sos")
                .setAllowedOrigins("http://localhost:5173", "http://10.131.6.124:5173")
                .withSockJS();
        
        // WebSocket endpoint for fare updates
        registry.addEndpoint("/ws-fare")
                .setAllowedOrigins("http://localhost:5173", "http://10.131.6.124:5173")
                .withSockJS();
    }
}
