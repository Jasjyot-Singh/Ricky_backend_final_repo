package com.example.ricky_backend_final.controller;

import com.example.ricky_backend_final.entity.SosAlert;
import com.example.ricky_backend_final.repository.SosRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/sos")
@CrossOrigin(origins = {"*"})
public class SosController {

    private final SimpMessagingTemplate messagingTemplate;
    private final SosRepository sosRepository;

    public SosController(SimpMessagingTemplate messagingTemplate, SosRepository sosRepository) {
        this.messagingTemplate = messagingTemplate;
        this.sosRepository = sosRepository;
    }

    @PostMapping("/alert")
    public ResponseEntity<SosAlert> receiveSosFromAutometer(@RequestBody Map<String, Object> sosData) {
        try {
            Double latitude = Double.parseDouble(sosData.get("latitude").toString());
            Double longitude = Double.parseDouble(sosData.get("longitude").toString());
            String driverId = sosData.getOrDefault("driverId", "AUTO-001").toString();

            // 🔴 NORMALIZE TYPE
            String rawType = sosData.getOrDefault("type", "SOS_BUTTON").toString();
            String normalizedType = rawType.equalsIgnoreCase("SOS_BUTTON")
                    ? "EMERGENCY"
                    : rawType.toUpperCase();

            SosAlert alert = new SosAlert();
            alert.setDriverId(driverId);
            alert.setLatitude(latitude);
            alert.setLongitude(longitude);
            alert.setType(normalizedType);
            alert.setStatus("ACTIVE");
            alert.setAcknowledged(false);

            SosAlert saved = sosRepository.save(alert);

            // ✅ Broadcast AFTER save
            messagingTemplate.convertAndSend("/topic/sos-alerts", saved);

            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @GetMapping("/alerts")
    public ResponseEntity<List<SosAlert>> getAllAlerts() {
        try {
            List<SosAlert> alerts = sosRepository.findAll();
            return ResponseEntity.ok(alerts);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/alerts/active")
    public ResponseEntity<List<SosAlert>> getActiveAlerts() {
        try {
            List<SosAlert> alerts = sosRepository.findByStatusOrderByTimestampDesc("ACTIVE");
            return ResponseEntity.ok(alerts);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/alerts/{alertId}/acknowledge")
    public ResponseEntity<SosAlert> acknowledgeAlert(@PathVariable String alertId) {
        try {
            return sosRepository.findById(alertId).map(alert -> {
                alert.setAcknowledged(true);
                alert.setStatus("RESOLVED");
                SosAlert saved = sosRepository.save(alert);

                // Notify admin panel of acknowledgment
                messagingTemplate.convertAndSend("/topic/sos-updates", saved);

                return ResponseEntity.ok(saved);
            }).orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
