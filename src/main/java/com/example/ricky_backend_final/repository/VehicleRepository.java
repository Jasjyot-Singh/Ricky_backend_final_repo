package com.example.ricky_backend_final.repository;

import com.example.ricky_backend_final.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, String> {
    Vehicle findByPlateNumber(String plateNumber);
}
