package com.example.ricky_backend_final.websockets.livelocation_websockets;

import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

public class Livelocationconfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config){
        config.enableSimpleBroker("/topic"); // enpoint for admin panel to receive the messages on this
        config.setApplicationDestinationPrefixes("/app"); //
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // WebSocket connection endpoint
        registry.addEndpoint("/ws-ricky").setAllowedOrigins("*").withSockJS();
    }
}
