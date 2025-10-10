package com.example.ricky_backend_final.websockets;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/sos")
public class SosController {

    private final SimpMessagingTemplate messagingTemplate;

    public SosController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    // Single endpoint for all SOS alerts
    @PostMapping
    public SosAlert handleSos(
            @RequestParam String type,       // e.g. CRASH, SOS_BUTTON, GEOFENCE
            @RequestParam double latitude,
            @RequestParam double longitude
    ) {
        String message = generateMessage(type);

        SosAlert alert = new SosAlert(
                "SOS-" + UUID.randomUUID(),
                type.toUpperCase(),
                latitude,
                longitude,
                LocalDateTime.now().toString(),
                message
        );

        // Send to single WebSocket topic for admin panel
        messagingTemplate.convertAndSend("/topic/sos-alerts", alert);

        return alert; // return response to device/client
    }

    private String generateMessage(String type) {
        return switch (type.toUpperCase()) {
            case "CRASH" -> "🚨 Crash detected! Possible accident nearby.";
            case "SOS_BUTTON" -> "🆘 SOS button manually pressed!";
            case "GEOFENCE" -> "⚠️ Vehicle exited designated geofence area.";
            case "OVERSPEED" -> "⚡ Overspeeding detected!";
            default -> "🔔 General SOS alert received.";
        };
    }
}
