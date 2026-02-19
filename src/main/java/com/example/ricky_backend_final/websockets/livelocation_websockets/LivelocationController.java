package com.example.ricky_backend_final.websockets.livelocation_websockets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class LivelocationController {

    private static final Logger log = LoggerFactory.getLogger(LivelocationController.class);

    private final SimpMessagingTemplate messagingTemplate;

    public LivelocationController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    /**
     * Client MUST send to: /app/LiveLocation
     */
    @MessageMapping("/LiveLocation")
    public void livelocation(com.example.ricky_backend_final.websockets.livelocation_websockets.LocationMessage message) {

        if (message == null) {
            log.warn("⚠️ Received null LiveLocation message");
            return;
        }

        String driverId = message.getDriverId();
        double lat = message.getLatitude();
        double lon = message.getLongitude();

        if (driverId == null || driverId.isBlank()) {
            log.warn("⚠️ DriverId is missing. Ignoring message.");
            return;
        }

        log.info("📍 LiveLocation received from driver: {}", driverId);
        log.info("📍 Coordinates: {}, {}", lat, lon);

        // -----------------------------
        // ALWAYS broadcast live location
        // -----------------------------
        String locationDestination = "/topic/location/" + driverId;
        messagingTemplate.convertAndSend(locationDestination, message);
        log.info("📡 Live location broadcasted to {}", locationDestination);
    }
}
