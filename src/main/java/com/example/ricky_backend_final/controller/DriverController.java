package com.example.ricky_backend_final.controller;

import com.example.ricky_backend_final.entity.Driver;
import com.example.ricky_backend_final.service.DriverService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/drivers")
@CrossOrigin(origins = {"*"})
public class DriverController {

    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    // Create a new driver
    @PostMapping
    public ResponseEntity<Driver> createDriver(@RequestBody Driver driver) {
        Driver savedDriver = driverService.saveDriver(driver);
        return new ResponseEntity<>(savedDriver, HttpStatus.CREATED);
    }

    // Update an existing driver
    @PutMapping("/{id}")
    public ResponseEntity<Driver> updateDriver(@PathVariable("id") String driverId,
                                               @RequestBody Driver driver) {
        Optional<Driver> existing = driverService.getDriverById(driverId);
        if (existing.isPresent()) {
            driver.setDriverId(driverId); // ensure ID is preserved
            Driver updatedDriver = driverService.saveDriver(driver);
            return ResponseEntity.ok(updatedDriver);
        }
        return ResponseEntity.notFound().build();
    }

    // Get driver by ID
    @GetMapping("/{id}")
    public ResponseEntity<Driver> getDriverById(@PathVariable("id") String driverId) {
        Optional<Driver> driver = driverService.getDriverById(driverId);
        return driver.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Get all drivers
    @GetMapping
    public ResponseEntity<List<Driver>> getAllDrivers() {
        List<Driver> drivers = driverService.getAllDrivers();
        return ResponseEntity.ok(drivers);
    }

    // Delete driver by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDriver(@PathVariable("id") String driverId) {
        driverService.deleteDriverById(driverId);
        return ResponseEntity.noContent().build();
    }

    // Search by driver name
    @GetMapping("/search/name")
    public ResponseEntity<List<Driver>> getDriversByName(@RequestParam String driverName) {
        List<Driver> drivers = driverService.getDriversByName(driverName);
        return ResponseEntity.ok(drivers);
    }

    // Search by driver phone
    @GetMapping("/search/phone")
    public ResponseEntity<List<Driver>> getDriversByPhone(@RequestParam String driverPhone) {
        List<Driver> drivers = driverService.getDriversByPhone(driverPhone);
        return ResponseEntity.ok(drivers);
    }

    // Search by license number
    @GetMapping("/search/license")
    public ResponseEntity<List<Driver>> getDriversByLicense(@RequestParam String licenseNumber) {
        List<Driver> drivers = driverService.getDriversByLicense(licenseNumber);
        return ResponseEntity.ok(drivers);
    }

    // Search by vehicle number
    @GetMapping("/search/vehicle")
    public ResponseEntity<List<Driver>> getDriversByVehicleNumber(@RequestParam String vehicleNumber) {
        List<Driver> drivers = driverService.getDriversByVehicleNumber(vehicleNumber);
        return ResponseEntity.ok(drivers);
    }
}
