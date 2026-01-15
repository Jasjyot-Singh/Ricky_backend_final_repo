package com.example.ricky_backend_final.websockets;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@RestController("sosWebsocketController")      // different bean name
@RequestMapping("/api/sos-websocket")          // different base path
@CrossOrigin(origins = {
        "http://localhost:3000",
        "http://localhost:5173",
        "http://10.131.6.124:5173",
        "https://anveshan-x-ricky-ap.vercel.app"
})
public class SosController {

    private static final String SOS_TOPIC = "/topic/sos-alerts";

    private final SimpMessagingTemplate messagingTemplate;

    public SosController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @PostMapping
    public ResponseEntity<?> receiveSos(@RequestBody Map<String, Object> payload) {

        String type = payload.getOrDefault("type", "SOS_BUTTON").toString();

        double latitude = payload.get("latitude") != null
                ? Double.parseDouble(payload.get("latitude").toString())
                : 0.0;

        double longitude = payload.get("longitude") != null
                ? Double.parseDouble(payload.get("longitude").toString())
                : 0.0;

        SosWebSocketMessage sosMessage = new SosWebSocketMessage(
                "SOS-" + UUID.randomUUID(),
                type,
                latitude,
                longitude,
                "ACTIVE",
                LocalDateTime.now()
        );

        messagingTemplate.convertAndSend(SOS_TOPIC, sosMessage);

        return ResponseEntity.ok(Map.of(
                "status", "RECEIVED",
                "id", sosMessage.id()
        ));
    }

    public record SosWebSocketMessage(
            String id,
            String type,
            double latitude,
            double longitude,
            String status,
            LocalDateTime timestamp
    ) {}
}
