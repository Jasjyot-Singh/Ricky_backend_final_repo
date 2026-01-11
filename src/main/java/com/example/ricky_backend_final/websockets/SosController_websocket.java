package com.example.ricky_backend_final.websockets;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/sos")
public class SosController_websocket {

    private final SimpMessagingTemplate messagingTemplate;

    public SosController_websocket(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    // Single endpoint for all SOS alerts
    @PostMapping
    public SosAlert handleSos(
            @RequestParam String type,       // CRASH, SOS_BUTTON, GEOFENCE, etc.
            @RequestParam double latitude,
            @RequestParam double longitude
    ) {
        // 🔴 NORMALIZE TYPE (CORE FIX)
        String normalizedType = normalizeType(type);

        String message = generateMessage(normalizedType);

        SosAlert alert = new SosAlert(
                "SOS-" + UUID.randomUUID(),
                normalizedType,
                latitude,
                longitude,
                LocalDateTime.now().toString(),
                message
        );

        // Send to WebSocket topic for admin panel
        messagingTemplate.convertAndSend("/topic/sos-alerts", alert);

        return alert;
    }

    // ✅ Backend normalization (production-safe)
    private String normalizeType(String type) {
        if (type == null) return "EMERGENCY";

        return switch (type.toUpperCase()) {
            case "SOS_BUTTON" -> "EMERGENCY";
            default -> type.toUpperCase();
        };
    }

    private String generateMessage(String type) {
        return switch (type) {
            case "CRASH" -> "Crash detected! Possible accident nearby.";
            case "EMERGENCY" -> "Emergency SOS triggered!";
            case "GEOFENCE" -> "Vehicle exited designated geofence area.";
            case "OVERSPEED" -> "Overspeeding detected!";
            default -> "General SOS alert received.";
        };
    }
}
