package com.example.ricky_backend_final.controller;

import com.example.ricky_backend_final.entity.FarePerRideData;
import com.example.ricky_backend_final.service.FarePerRideService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fares")
@CrossOrigin(origins = {"http://localhost:5173", "http://10.131.6.124:5173"})
public class FarePerRideController {

    private final FarePerRideService farePerRideService;

    public FarePerRideController(FarePerRideService farePerRideService) {
        this.farePerRideService = farePerRideService;
    }

    @PostMapping("/autometer")
    public ResponseEntity<FarePerRideData> saveFareDataFromAutometer(@RequestBody FarePerRideData fareData) {
        FarePerRideData saved = farePerRideService.saveFareData(fareData);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/autometer/recent")
    public ResponseEntity<List<FarePerRideData>> getRecentFares() {
        List<FarePerRideData> fares = farePerRideService.getRecentFareData();
        return ResponseEntity.ok(fares);
    }

    @GetMapping("/autometer/driver/{driverId}")
    public ResponseEntity<List<FarePerRideData>> getFaresByDriver(@PathVariable String driverId) {
        List<FarePerRideData> fares = farePerRideService.getFareDataByDriver(driverId);
        return ResponseEntity.ok(fares);
    }
}
