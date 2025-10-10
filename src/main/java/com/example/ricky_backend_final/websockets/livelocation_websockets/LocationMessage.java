package com.example.ricky_backend_final.websockets.livelocation_websockets;

public class LocationMessage {
    private String driverId;
    private double latitude;
    private double longitude;

    public LocationMessage() {}
    public LocationMessage(String driverId, double latitude, double longitude) {
        this.driverId = driverId;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    // getters and setters
    public String getDriverId() { return driverId; }
    public void setDriverId(String driverId) { this.driverId = driverId; }
    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }
    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }
}

