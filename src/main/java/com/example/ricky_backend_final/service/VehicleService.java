package com.example.ricky_backend_final.service;

import com.example.ricky_backend_final.entity.Driver;
import com.example.ricky_backend_final.entity.Vehicle;
import com.example.ricky_backend_final.repository.DriverRepository;
import com.example.ricky_backend_final.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepo;
    private final DriverRepository driverRepo;

    public VehicleService(VehicleRepository vehicleRepo, DriverRepository driverRepo) {
        this.vehicleRepo = vehicleRepo;
        this.driverRepo = driverRepo;
    }

    public List<Vehicle> getAllVehicles() {
        return vehicleRepo.findAll();
    }

    public Optional<Vehicle> getVehicleById(String id) {
        return vehicleRepo.findById(id);
    }

    public Vehicle assignVehicleToDriver(String driverId, Vehicle vehicle) {
        Driver driver = driverRepo.findById(driverId)
                .orElseThrow(() -> new RuntimeException("Driver not found: " + driverId));

        // link both sides
        vehicle.setDriver(driver);
        driver.setVehicle(vehicle);

        driverRepo.save(driver); // cascade ensures vehicle also gets saved
        return vehicle;
    }

    public void deleteVehicle(String vehicleId) {
        vehicleRepo.deleteById(vehicleId);
    }
}
