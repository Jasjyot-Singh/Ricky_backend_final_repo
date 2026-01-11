package com.example.ricky_backend_final.controller;



import com.example.ricky_backend_final.entity.Daily_driver_score_data;
import com.example.ricky_backend_final.service.Driver_score_service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/driver-scores")
public class Driver_score_controller {

    private final Driver_score_service driverScoreService;

    public Driver_score_controller(Driver_score_service driverScoreService) {
        this.driverScoreService = driverScoreService;
    }

    @PostMapping
    public ResponseEntity<Daily_driver_score_data> createScore(@RequestBody Daily_driver_score_data score) {
        return ResponseEntity.ok(driverScoreService.saveDailyScore(score));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Daily_driver_score_data> getScoreById(@PathVariable String id) {
        Daily_driver_score_data score = driverScoreService.getById(id);
        if (score == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(score);
    }

    @GetMapping
    public ResponseEntity<List<Daily_driver_score_data>> getAllScores() {
        return ResponseEntity.ok(driverScoreService.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteScoreById(@PathVariable String id) {
        driverScoreService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/driver/{driverId}")
    public ResponseEntity<List<Daily_driver_score_data>> getScoresByDriver(@PathVariable String driverId) {
        return ResponseEntity.ok(driverScoreService.getScoresByDriver(driverId));
    }

    @GetMapping("/license/{license}")
    public ResponseEntity<List<Daily_driver_score_data>> getScoresByLicense(@PathVariable String license) {
        return ResponseEntity.ok(driverScoreService.getScoresByLicense(license));
    }

    @GetMapping("/driver/{driverId}/monthly")
    public ResponseEntity<Double> getMonthlyScore(@PathVariable String driverId) {
        double score = driverScoreService.calculateMonthlyScore(driverId);
        return ResponseEntity.ok(score);
    }
}
