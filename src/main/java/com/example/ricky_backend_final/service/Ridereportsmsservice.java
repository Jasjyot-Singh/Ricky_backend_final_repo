package com.example.ricky_backend_final.service;



import com.example.ricky_backend_final.entity.Driver;
import com.example.ricky_backend_final.entity.FarePerRideData;
import com.example.ricky_backend_final.repository.DriverRepository;
import com.example.ricky_backend_final.repository.FarePerRideRepository;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

@Service
public class Ridereportsmsservice {

    private final DriverRepository driverRepository;
    private final FarePerRideRepository fareRepository;

    // Replace with your Fast2SMS API key
    private static final String FAST2SMS_API_KEY = "Z1T03xt9hC2nUirj4lSFafb8WEysQkcPIuROvBzqMgJXp6eGNmwO0582fjSdYIKcxz7iCWrD3vpuenUq";


    public Ridereportsmsservice(DriverRepository driverRepository, FarePerRideRepository fareRepository) {
        this.driverRepository = driverRepository;
        this.fareRepository = fareRepository;
    }

    public boolean sendDailySummary(String driverId) {
        try {
            // 1. Get driver
            Driver driver = driverRepository.findById(driverId).orElseThrow(() ->
                    new RuntimeException("Driver not found"));

            // 2. Fetch today's fares
            LocalDate today = LocalDate.now();
            List<FarePerRideData> fares = fareRepository.findByDriverIdOrderByReceivedAtDesc(driverId);
            // Filter only today's fares
            fares.removeIf(f -> !f.getReceivedAt().toLocalDate().equals(today));

            // 3. Summarize fares
            double totalFare = fares.stream()
                    .mapToDouble(f -> f.getFareAmount() != null ? f.getFareAmount() : 0)
                    .sum();
            int totalRides = fares.size();
            double totalDistance = fares.stream()
                    .mapToDouble(f -> f.getDistanceKm() != null ? f.getDistanceKm() : 0)
                    .sum();

            String message = "Hello " + driver.getDriverName() + "!\n" +
                    "Today's Ride Summary:\n" +
                    "Total Rides: " + totalRides + "\n" +
                    "Total Fare: ₹" + totalFare + "\n" +
                    "Total Distance: " + totalDistance + " km";

            // 4. Send SMS via Fast2SMS
            String url = "https://www.fast2sms.com/dev/bulkV2" +
                    "?authorization=" + FAST2SMS_API_KEY +
                    "&sender_id=FSTSMS" +
                    "&message=" + URLEncoder.encode(message, StandardCharsets.UTF_8) +
                    "&language=english&route=v3" +
                    "&numbers=" + driver.getDriverPhone();

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode() == 200;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

