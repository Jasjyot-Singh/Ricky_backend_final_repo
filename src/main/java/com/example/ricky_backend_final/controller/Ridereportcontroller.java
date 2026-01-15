package com.example.ricky_backend_final.controller;



import com.example.ricky_backend_final.service.Ridereportsmsservice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ride-report")
@CrossOrigin(origins = {"http://localhost:3000",
            "http://localhost:5173",
            "http://10.131.6.124:5173",
            "https://anveshan-x-ricky-ap.vercel.app"})
public class Ridereportcontroller {

    private final Ridereportsmsservice rideReportSmsService;

    public Ridereportcontroller(Ridereportsmsservice rideReportSmsService) {
        this.rideReportSmsService = rideReportSmsService;
    }

    @PostMapping("/send/{driverId}")
    public ResponseEntity<String> sendRideReport(@PathVariable String driverId) {
        boolean success = rideReportSmsService.sendDailySummary(driverId);
        if (success) {
            return ResponseEntity.ok("Ride report SMS sent successfully!");
        } else {
            return ResponseEntity.status(500).body("Failed to send SMS.");
        }
    }
}

