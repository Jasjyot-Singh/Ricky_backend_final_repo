package com.example.ricky_backend_final.repository;

import com.example.ricky_backend_final.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DriverRepository extends JpaRepository<Driver, String> {

    // Find drivers by exact name
    List<Driver> findByDriverName(String driverName);

    // Find drivers by phone
    List<Driver> findByDriverPhone(String driverPhone);

    // Find drivers by license number
    List<Driver> findByLicenseNumber(String licenseNumber);

    // Find driver by vehicle number using a JOIN query
    @Query("SELECT d FROM Driver d JOIN d.vehicle v WHERE v.plateNumber = :plateNumber")
    List<Driver> findByVehiclePlateNumber(@Param("plateNumber") String plateNumber);
}
