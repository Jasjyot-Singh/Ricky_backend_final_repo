package com.example.ricky_backend_final.websockets;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/sos")
public class SosController {

    private static final String SOS_TOPIC = "/topic/sos-alerts";
    private static final Logger log = LoggerFactory.getLogger(SosController.class);

    private final SimpMessagingTemplate messagingTemplate;

    public SosController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    /**
     * Called by Raspberry Pi (Python)
     */
    @PostMapping
    public ResponseEntity<?> receiveSos(@RequestBody SosRequest request) {

        log.info("🚨 /api/sos endpoint HIT at {}", LocalDateTime.now());
        log.info("📥 Incoming SOS Payload: {}", request);

        try {

            // Safe defaults
            String type = request.type() != null ? request.type() : "SOS_BUTTON";
            double latitude = request.latitude() != null ? request.latitude() : 0.0;
            double longitude = request.longitude() != null ? request.longitude() : 0.0;

            log.info("🔍 Processed values → type: {}, lat: {}, lon: {}", type, latitude, longitude);

            // Create WebSocket payload
            SosWebSocketMessage message = new SosWebSocketMessage(
                    "SOS-" + UUID.randomUUID(),
                    type,
                    latitude,
                    longitude,
                    "ACTIVE",
                    LocalDateTime.now()
            );

            log.info("📡 Broadcasting SOS to topic {}", SOS_TOPIC);

            messagingTemplate.convertAndSend(SOS_TOPIC, message);

            log.info("✅ WebSocket broadcast successful. ID: {}", message.id());

            return ResponseEntity.ok(Map.of(
                    "status", "RECEIVED",
                    "id", message.id()
            ));

        } catch (Exception e) {

            log.error("❌ ERROR while processing SOS request", e);

            return ResponseEntity.internalServerError().body(
                    Map.of(
                            "status", "ERROR",
                            "message", e.getMessage()
                    )
            );
        }
    }

    /**
     * Incoming request DTO
     */
    public record SosRequest(
            String type,
            Double latitude,
            Double longitude
    ) {}

    /**
     * Outgoing WebSocket payload
     */
    public record SosWebSocketMessage(
            String id,
            String type,
            double latitude,
            double longitude,
            String status,

            @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            LocalDateTime timestamp
    ) {}
}