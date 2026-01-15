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

        // Where server sends messages to subscribers
        config.enableSimpleBroker(
                "/topic",
                "/queue"
        );

        // Where clients SEND messages (@MessageMapping)
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {

        // RAW WebSocket endpoint (NO SockJS)
        registry
                .addEndpoint("/ws-ricky")
                .setAllowedOriginPatterns("*");
    }
}
