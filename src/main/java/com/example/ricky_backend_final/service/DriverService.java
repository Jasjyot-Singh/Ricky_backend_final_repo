package com.example.ricky_backend_final.service;

import com.example.ricky_backend_final.entity.Driver;
import com.example.ricky_backend_final.repository.DriverRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DriverService {

    private final DriverRepository driverRepository;

    public DriverService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    // Create or Update a driver
    public Driver saveDriver(Driver driver) {
        return driverRepository.save(driver);
    }

    // Get driver by ID
    public Optional<Driver> getDriverById(String driverId) {
        return driverRepository.findById(driverId);
    }

    // Get all drivers
    public List<Driver> getAllDrivers() {
        return driverRepository.findAll();
    }

    // Delete driver by ID
    public void deleteDriverById(String driverId) {
        driverRepository.deleteById(driverId);
    }

    // Search by driver name
    public List<Driver> getDriversByName(String driverName) {
        return driverRepository.findByDriverName(driverName);
    }

    // Search by driver phone
    public List<Driver> getDriversByPhone(String driverPhone) {
        return driverRepository.findByDriverPhone(driverPhone);
    }

    // Search by license number
    public List<Driver> getDriversByLicense(String licenseNumber) {
        return driverRepository.findByLicenseNumber(licenseNumber);
    }

    // Search by vehicle number
    public List<Driver> getDriversByVehicleNumber(String vehicleNumber) {
        return driverRepository.findByVehiclePlateNumber(vehicleNumber);
    }
}
