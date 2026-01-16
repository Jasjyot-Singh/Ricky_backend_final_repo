package com.example.ricky_backend_final.websockets.livelocation_websockets;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;



// geifencing also implemented in this

@Controller
public class LivelocationController {

    private static final double CENTER_LAT = 28.6139; // latitude for the center
    private static final double CENTER_LON = 77.2090; // longitude for the center
    private static final double RADIUS_METERS = 30;

    private final SimpMessagingTemplate messagingTemplate;

    LivelocationController(SimpMessagingTemplate messagingTemplate){
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/LiveLocation")
    public void livelocation(LocationMessage message){
        Double lat = message.getLatitude();
        Double lon = message.getLongitude();

        boolean outside = isOutsideGeofence(lat, lon, CENTER_LAT, CENTER_LON, RADIUS_METERS);

        if (outside) {
            // Send alert to all subscribed clients
            messagingTemplate.convertAndSend("/topic/alerts",
                    "⚠️ ALERT: Driver exited geofence at lat=" + lat + ", lon=" + lon);
        } else {
            // Just show current location for debugging/demo
            messagingTemplate.convertAndSend("/topic/location", message);
        }
    }

    private boolean isOutsideGeofence(double lat, double lon,
                                      double centerLat, double centerLon, double radiusMeters) {
        double earthRadius = 6371000; // meters

        double dLat = Math.toRadians(lat - centerLat);
        double dLon = Math.toRadians(lon - centerLon);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(centerLat)) * Math.cos(Math.toRadians(lat))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double distance = earthRadius * c;
        return distance > radiusMeters;
    }
}

