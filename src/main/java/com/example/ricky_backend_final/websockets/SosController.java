
package com.example.ricky_backend_final.websockets;


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

    private final SimpMessagingTemplate messagingTemplate;

    public SosController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    /**
     * This endpoint is called by your PyQt app:
     * requests.post(base_url, json=payload)
     */
    @PostMapping
    public ResponseEntity<?> receiveSos(@RequestBody Map<String, Object> payload) {

        // 🔒 Defensive parsing (matches your Python client)
        String type = payload.getOrDefault("type", "SOS_BUTTON").toString();

        double latitude = payload.get("latitude") != null
                ? Double.parseDouble(payload.get("latitude").toString())
                : 0.0;

        double longitude = payload.get("longitude") != null
                ? Double.parseDouble(payload.get("longitude").toString())
                : 0.0;

        // ✅ Create SOS message (NO DB)
        SosWebSocketMessage sosMessage = new SosWebSocketMessage(
                "SOS-" + UUID.randomUUID(),
                type,
                latitude,
                longitude,
                "ACTIVE",
                LocalDateTime.now()
        );

        // 🚀 PUSH TO WEBSOCKET (ADMIN PANEL)
        messagingTemplate.convertAndSend(SOS_TOPIC, sosMessage);

        // ✅ Respond back to PyQt
        return ResponseEntity.ok(Map.of(
                "status", "RECEIVED",
                "id", sosMessage.id()
        ));
    }

    /**
     * Immutable WebSocket payload
     * (Java 17+ record, fast & clean)
     */
    public record SosWebSocketMessage(
            String id,
            String type,
            double latitude,
            double longitude,
            String status,
            LocalDateTime timestamp
    ) {}
}
