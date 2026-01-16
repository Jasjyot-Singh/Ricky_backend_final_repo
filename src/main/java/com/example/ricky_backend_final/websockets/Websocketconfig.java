package com.example.ricky_backend_final.websockets;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class Websocketconfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {

        // Server → Client (Admin Panel subscribes here)
        config.enableSimpleBroker("/topic", "/queue");

        // Client → Server (LiveLocationController uses this)
        // @MessageMapping("/LiveLocation") → /app/LiveLocation
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {

        // Single WebSocket endpoint for EVERYTHING
        registry
            .addEndpoint("/ws-ricky")
            .setAllowedOrigins("*")
            .withSockJS();
    }
}
