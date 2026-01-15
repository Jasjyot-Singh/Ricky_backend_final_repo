package com.example.ricky_backend_final.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "autometer_fare_data")
public class FarePerRideData {

    @Id
    @Column(length = 50)
    private String id;

    @JsonProperty("ride_id")
    @Column(nullable = false)
    private String rideId;

    @JsonProperty("driver_id")
    @Column(nullable = false)
    private String driverId;

    @JsonProperty("passenger_id")
    @Column(nullable = false)
    private String passengerId;

    @JsonProperty("start_time")
    @Column(nullable = false)
    private LocalDateTime startTime;

    @JsonProperty("end_time")
    @Column(nullable = false)
    private LocalDateTime endTime;

    @JsonProperty("start_latitude")
    private Double startLatitude;

    @JsonProperty("start_longitude")
    private Double startLongitude;

    @JsonProperty("end_latitude")
    private Double endLatitude;

    @JsonProperty("end_longitude")
    private Double endLongitude;

    @JsonProperty("total_distance_km")
    private Double distanceKm;

    @JsonProperty("fare_amount")
    private Double fareAmount;

    @JsonProperty("fare_rate_per_km")
    private Double fareRate;

    @Column(nullable = false)
    private LocalDateTime receivedAt;

    @PrePersist
    public void prePersist() {
        if (id == null) {
            this.id = "AUTO-FARE-" + UUID.randomUUID().toString();
        }
        if (receivedAt == null) {
            this.receivedAt = LocalDateTime.now();
        }
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getRideId() { return rideId; }
    public void setRideId(String rideId) { this.rideId = rideId; }

    public String getDriverId() { return driverId; }
    public void setDriverId(String driverId) { this.driverId = driverId; }

    public String getPassengerId() { return passengerId; }
    public void setPassengerId(String passengerId) { this.passengerId = passengerId; }

    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }

    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }

    public Double getStartLatitude() { return startLatitude; }
    public void setStartLatitude(Double startLatitude) { this.startLatitude = startLatitude; }

    public Double getStartLongitude() { return startLongitude; }
    public void setStartLongitude(Double startLongitude) { this.startLongitude = startLongitude; }

    public Double getEndLatitude() { return endLatitude; }
    public void setEndLatitude(Double endLatitude) { this.endLatitude = endLatitude; }

    public Double getEndLongitude() { return endLongitude; }
    public void setEndLongitude(Double endLongitude) { this.endLongitude = endLongitude; }

    public Double getDistanceKm() { return distanceKm; }
    public void setDistanceKm(Double distanceKm) { this.distanceKm = distanceKm; }

    public Double getFareAmount() { return fareAmount; }
    public void setFareAmount(Double fareAmount) { this.fareAmount = fareAmount; }

    public Double getFareRate() { return fareRate; }
    public void setFareRate(Double fareRate) { this.fareRate = fareRate; }

    public LocalDateTime getReceivedAt() { return receivedAt; }
    public void setReceivedAt(LocalDateTime receivedAt) { this.receivedAt = receivedAt; }
}