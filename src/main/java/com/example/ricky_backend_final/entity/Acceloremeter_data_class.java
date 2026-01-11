package com.example.ricky_backend_final.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
public class Acceloremeter_data_class {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String driverId; // just store driverId string // no many to oneconnection here so that it doen't actually create unnecessary object in the driver class

    private Timestamp timestamp;

    private Double lat;
    private Double lon;

    private Double Accel_x;
    private Double Accel_y;
    private Double Accel_z;
    private Double Gyro_x;
    private Double Gyro_y;
    private Double Gyro_z;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDriver_id() {
        return driverId;
    }

    public void setDriver_id(String driver_id) {
        this.driverId = driver_id;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getAccel_x() {
        return Accel_x;
    }

    public void setAccel_x(Double accel_x) {
        Accel_x = accel_x;
    }

    public Double getAccel_y() {
        return Accel_y;
    }

    public void setAccel_y(Double accel_y) {
        Accel_y = accel_y;
    }

    public Double getAccel_z() {
        return Accel_z;
    }

    public void setAccel_z(Double accel_z) {
        Accel_z = accel_z;
    }

    public Double getGyro_x() {
        return Gyro_x;
    }

    public void setGyro_x(Double gyro_x) {
        Gyro_x = gyro_x;
    }

    public Double getGyro_y() {
        return Gyro_y;
    }

    public void setGyro_y(Double gyro_y) {
        Gyro_y = gyro_y;
    }

    public Double getGyro_z() {
        return Gyro_z;
    }

    public void setGyro_z(Double gyro_z) {
        Gyro_z = gyro_z;
    }
}



