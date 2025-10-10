package com.example.ricky_backend_final.websockets;

public class SosAlert {

    private String id;
    private String type; // CRASH, SOS_BUTTON, GEOFENCE, etc.
    private double latitude;
    private double longitude;
    private String timestamp;
    private String message;

    public SosAlert(String id, String type, double latitude, double longitude,
                    String timestamp, String message) {
        this.id = id;
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timestamp = timestamp;
        this.message = message;
    }

    // Getters and setters
    public String getId() { return id; }
    public String getType() { return type; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    public String getTimestamp() { return timestamp; }
    public String getMessage() { return message; }

    public void setId(String id) { this.id = id; }
    public void setType(String type) { this.type = type; }
    public void setLatitude(double latitude) { this.latitude = latitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
    public void setMessage(String message) { this.message = message; }
}


