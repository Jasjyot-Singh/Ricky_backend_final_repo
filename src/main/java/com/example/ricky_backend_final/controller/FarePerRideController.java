package com.example.ricky_backend_final.controller;

import com.example.ricky_backend_final.entity.FarePerRideData;
import com.example.ricky_backend_final.service.FarePerRideService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fares")
@CrossOrigin(origins = "*") // for production, restrict origins
public class FarePerRideController {

    private final FarePerRideService farePerRideService;

    public FarePerRideController(FarePerRideService farePerRideService) {
        this.farePerRideService = farePerRideService;
    }

    /**
     * Receives fare data from Autometer (Python client)
     */
    @PostMapping("/autometer")
    public ResponseEntity<FarePerRideData> saveFareDataFromAutometer(@RequestBody FarePerRideData fareData) {
        // Ensure IDs and timestamps are valid
        FarePerRideData saved = farePerRideService.saveFareData(fareData);
        return ResponseEntity.ok(saved);
    }

    /**
     * Fetch recent fare records (limit to last N if needed in service)
     */
    @GetMapping("/autometer/recent")
    public ResponseEntity<List<FarePerRideData>> getRecentFares() {
        List<FarePerRideData> fares = farePerRideService.getRecentFareData();
        return ResponseEntity.ok(fares);
    }

    /**
     * Fetch fares by driver ID
     */
    @GetMapping("/autometer/driver/{driverId}")
    public ResponseEntity<List<FarePerRideData>> getFaresByDriver(@PathVariable String driverId) {
        List<FarePerRideData> fares = farePerRideService.getFareDataByDriver(driverId);
        return ResponseEntity.ok(fares);
    }
}
