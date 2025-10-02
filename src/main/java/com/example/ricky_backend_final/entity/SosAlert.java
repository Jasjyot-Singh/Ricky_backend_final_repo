package com.example.ricky_backend_final.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "sos_alerts")
public class SosAlert {
    @Id
    private String id;
    
    private String driverId;
    private Double latitude;
    private Double longitude;
    private LocalDateTime timestamp;
    private Boolean acknowledged = false;
    private String status = "ACTIVE"; // ACTIVE, RESOLVED, CANCELLED

    @PrePersist
    public void prePersist() {
        if (id == null) {
            this.id = "SOS-" + UUID.randomUUID().toString();
        }
        if (timestamp == null) {
            this.timestamp = LocalDateTime.now();
        }
    }

    // Constructors
    public SosAlert() {}

    public SosAlert(String driverId, Double latitude, Double longitude) {
        this.driverId = driverId;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getDriverId() { return driverId; }
    public void setDriverId(String driverId) { this.driverId = driverId; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public Boolean getAcknowledged() { return acknowledged; }
    public void setAcknowledged(Boolean acknowledged) { this.acknowledged = acknowledged; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
