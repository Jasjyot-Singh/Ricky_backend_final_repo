package com.example.ricky_backend_final.controller;

import com.example.ricky_backend_final.entity.FareRate;
import com.example.ricky_backend_final.service.FareRateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/fare")
@CrossOrigin(origins = {"*"})
public class FareRateController {

    private final FareRateService fareRateService;

    public FareRateController(FareRateService fareRateService) {
        this.fareRateService = fareRateService;
    }

    @GetMapping("/get")
    public ResponseEntity<Map<String, Object>> getCurrentFareRate() {
        FareRate fareRate = fareRateService.getCurrentFareRate();
        Map<String, Object> response = new HashMap<>();
        response.put("fare_rate", fareRate != null ? fareRate.getRate() : 12.0);
        response.put("last_updated", fareRate != null ? fareRate.getUpdatedAt() : null);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/change")
    public ResponseEntity<Map<String, Object>> updateFareRate(@RequestParam double newRate) {
        FareRate updated = fareRateService.updateFareRate(newRate, "RTO_ADMIN");
        Map<String, Object> response = new HashMap<>();
        response.put("success", updated != null);
        return ResponseEntity.ok(response);
    }
}
