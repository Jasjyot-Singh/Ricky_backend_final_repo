package com.example.ricky_backend_final.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.UUID;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "drivers")
public class Driver {

    @Id
    private String driverId;

    @Column(nullable = false)
    private String driverName;

    @Column(nullable = false, unique = true)
    private String driverPhone;

    @Column(nullable = false, unique = true)
    private String licenseNumber;

    @Column(nullable = false)
    private boolean active = true;

    @OneToOne(mappedBy = "driver", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Vehicle vehicle;


    @PrePersist
    public void prePersist() {
        if (driverId == null) {
            this.driverId = "DRIVER-" + UUID.randomUUID().toString();
        }
    }

    // Getters & Setters
    public String getDriverId() { return driverId; }
    public void setDriverId(String driverId) { this.driverId = driverId; }

    public String getDriverName() { return driverName; }
    public void setDriverName(String driverName) { this.driverName = driverName; }

    public String getDriverPhone() { return driverPhone; }
    public void setDriverPhone(String driverPhone) { this.driverPhone = driverPhone; }

    public String getLicenseNumber() { return licenseNumber; }
    public void setLicenseNumber(String licenseNumber) { this.licenseNumber = licenseNumber; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public Vehicle getVehicle() { return vehicle; }
    public void setVehicle(Vehicle vehicle) { this.vehicle = vehicle; }
}
