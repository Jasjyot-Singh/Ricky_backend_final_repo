package com.example.ricky_backend_final.websockets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

@Configuration
@EnableWebSocketMessageBroker
public class Websocketconfig implements WebSocketMessageBrokerConfigurer {

    private static final Logger log = LoggerFactory.getLogger(Websocketconfig.class);

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {

        log.info("🔧 Configuring Message Broker...");

        config.enableSimpleBroker("/topic", "/queue")
                .setHeartbeatValue(new long[]{10000, 10000})   // 10 sec heartbeats
                .setTaskScheduler(taskScheduler());           // 🔥 REQUIRED for heartbeat

        config.setApplicationDestinationPrefixes("/app");

        log.info("✅ Message Broker Configured (Heartbeat enabled)");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {

        log.info("🔌 Registering WebSocket Endpoints...");

        // Device endpoint (Raw WebSocket)
        registry
                .addEndpoint("/ws-device")
                .setAllowedOriginPatterns("*");

        log.info("✅ Endpoint Registered: /ws-device (RAW WebSocket)");

        // Browser endpoint (SockJS)
        registry
                .addEndpoint("/ws-browser")
                .setAllowedOriginPatterns("*")
                .withSockJS();

        log.info("✅ Endpoint Registered: /ws-browser (SockJS enabled)");
    }

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {

        log.info("⚙️ Configuring WebSocket Transport Limits...");

        registration.setMessageSizeLimit(64 * 1024);      // 64 KB
        registration.setSendBufferSizeLimit(512 * 1024);  // 512 KB
        registration.setSendTimeLimit(20 * 1000);         // 20 sec

        log.info("✅ WebSocket Transport Configured");
    }

    // 🔥 Required when heartbeat is enabled
    @Bean
    public ThreadPoolTaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(1);
        scheduler.setThreadNamePrefix("wss-heartbeat-thread-");
        scheduler.initialize();
        return scheduler;
    }
}
