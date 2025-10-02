package com.example.ricky_backend_final.controller;

import com.example.ricky_backend_final.entity.Vehicle;
import com.example.ricky_backend_final.service.VehicleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
@CrossOrigin(origins = {"http://localhost:5173", "http://10.131.6.124:5173"})
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping
    public ResponseEntity<List<Vehicle>> getAllVehicles() {
        return ResponseEntity.ok(vehicleService.getAllVehicles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> getVehicleById(@PathVariable String id) {
        return vehicleService.getVehicleById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Assign a vehicle to a driver
    @PostMapping("/assign/{driverId}")
    public ResponseEntity<Vehicle> assignVehicle(
            @PathVariable String driverId,
            @RequestBody Vehicle vehicle) {
        return ResponseEntity.ok(vehicleService.assignVehicleToDriver(driverId, vehicle));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable String id) {
        vehicleService.deleteVehicle(id);
        return ResponseEntity.noContent().build();
    }
}
