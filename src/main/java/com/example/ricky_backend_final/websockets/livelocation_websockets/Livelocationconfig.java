package com.example.ricky_backend_final.websockets.livelocation_websockets;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class Livelocationconfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Server → clients
        config.enableSimpleBroker("/topic", "/queue");
        // Clients → server (for @MessageMapping, if you add them later)
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry
            .addEndpoint("/ws-ricky")
            .setAllowedOrigins(
                "http://localhost:5173",
                "http://localhost:3000",
                "https://anveshan-x-ricky-ap.vercel.app"
            );
        // No SockJS; your frontend uses native WebSocket
    }
}
