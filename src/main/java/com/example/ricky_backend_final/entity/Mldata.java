package com.example.ricky_backend_final.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Mldata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double lat;
    private double lon;
    private int potholeLabel;   // 0 or 1
    private double confidence;

    public Mldata() {}

    public Mldata(double lat, double lon, int potholeLabel, double confidence) {
        this.lat = lat;
        this.lon = lon;
        this.potholeLabel = potholeLabel;
        this.confidence = confidence;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public double getLat() { return lat; }
    public void setLat(double lat) { this.lat = lat; }
    public double getLon() { return lon; }
    public void setLon(double lon) { this.lon = lon; }
    public int getPotholeLabel() { return potholeLabel; }
    public void setPotholeLabel(int potholeLabel) { this.potholeLabel = potholeLabel; }
    public double getConfidence() { return confidence; }
    public void setConfidence(double confidence) { this.confidence = confidence; }
}

