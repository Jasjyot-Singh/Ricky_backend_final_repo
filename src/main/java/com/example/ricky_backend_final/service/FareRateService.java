package com.example.ricky_backend_final.service;

import com.example.ricky_backend_final.entity.FareRate;
import com.example.ricky_backend_final.repository.FareRateRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class FareRateService {

    private final FareRateRepository fareRateRepository;

    public FareRateService(FareRateRepository fareRateRepository) {
        this.fareRateRepository = fareRateRepository;
    }

    public FareRate getCurrentFareRate() {
        return fareRateRepository.findTopByActiveOrderByUpdatedAtDesc(true).orElse(null);
    }

    public FareRate updateFareRate(double newRate, String updatedBy) {
        Optional<FareRate> optionalFareRate = fareRateRepository.findTopByActiveOrderByUpdatedAtDesc(true);
        FareRate fareRate;
        if(optionalFareRate.isPresent()) {
            fareRate = optionalFareRate.get();
            fareRate.setRate(newRate);
            fareRate.setUpdatedAt(LocalDateTime.now());
            fareRate.setUpdatedBy(updatedBy);
        } else {
            fareRate = new FareRate();
            fareRate.setRate(newRate);
            fareRate.setUpdatedAt(LocalDateTime.now());
            fareRate.setUpdatedBy(updatedBy);
            fareRate.setActive(true);
        }
        return fareRateRepository.save(fareRate);
    }
}
