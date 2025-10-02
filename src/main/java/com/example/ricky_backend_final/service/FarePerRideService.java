package com.example.ricky_backend_final.service;

import com.example.ricky_backend_final.entity.FarePerRideData;
import com.example.ricky_backend_final.repository.FarePerRideRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FarePerRideService {

    private final FarePerRideRepository farePerRideRepository;

    public FarePerRideService(FarePerRideRepository farePerRideRepository) {
        this.farePerRideRepository = farePerRideRepository;
    }

    public FarePerRideData saveFareData(FarePerRideData fareData) {
        return farePerRideRepository.save(fareData);
    }

    public List<FarePerRideData> getRecentFareData() {
        return farePerRideRepository.findByReceivedAtAfterOrderByReceivedAtDesc(LocalDateTime.now().minusDays(1));
    }

    public List<FarePerRideData> getFareDataByDriver(String driverId) {
        return farePerRideRepository.findByDriverIdOrderByReceivedAtDesc(driverId);
    }
}
