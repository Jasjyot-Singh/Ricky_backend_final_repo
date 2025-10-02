package com.example.ricky_backend_final.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.UUID;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "vehicles")
public class Vehicle {

    @Id
    private String vehicleId;

    private String plateNumber;

    @OneToOne
    @JoinColumn(name = "driver_id", unique = true)
    @JsonBackReference
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Driver driver;


    @PrePersist
    public void prePersist() {
        if (vehicleId == null) {
            this.vehicleId = "VEH-" + UUID.randomUUID().toString();
        }
    }

    // getters & setters
    public String getVehicleId() { return vehicleId; }
    public void setVehicleId(String vehicleId) { this.vehicleId = vehicleId; }

    public String getPlateNumber() { return plateNumber; }
    public void setPlateNumber(String plateNumber) { this.plateNumber = plateNumber; }

    public Driver getDriver() { return driver; }
    public void setDriver(Driver driver) { this.driver = driver; }
}
