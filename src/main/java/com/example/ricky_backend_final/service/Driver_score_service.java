package com.example.ricky_backend_final.service;

import com.example.ricky_backend_final.entity.Daily_driver_score_data;
import com.example.ricky_backend_final.repository.Driver_score_repository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
public class Driver_score_service {

    @Autowired
    private final Driver_score_repository driverScoreRepository;



    public Driver_score_service(Driver_score_repository driverScoreRepository) {
        this.driverScoreRepository = driverScoreRepository;
    }

    public Daily_driver_score_data saveDailyScore(Daily_driver_score_data score) {
        return driverScoreRepository.save(score);
    }

    public Daily_driver_score_data getById(String id) {
        return driverScoreRepository.findById(id).orElse(null);
    }

    public List<Daily_driver_score_data> getAll() {
        return driverScoreRepository.findAll();
    }

    public void deleteById(String id) {
        driverScoreRepository.deleteById(id);
    }

    public void delete(Daily_driver_score_data score) {
        driverScoreRepository.delete(score);
    }

    public List<Daily_driver_score_data> getScoresByDriver(String driverId) {
        return driverScoreRepository.findByDriverDriverId(driverId);
    }

    public List<Daily_driver_score_data> getScoresByLicense(String license) {
        return driverScoreRepository.findByDriverLicenseNumber(license);
    }

    @Transactional
    public double calculateMonthlyScore(String driverId) {
        YearMonth currentMonth = YearMonth.now();
        LocalDate start = currentMonth.atDay(1);
        LocalDate end = LocalDate.now();

        List<Daily_driver_score_data> dailyScores =
                driverScoreRepository.findByDriverIdAndDateBetween(driverId, start, end);

        if (dailyScores.isEmpty()) return 0.0;

        double average = dailyScores.stream()
                .mapToDouble(Daily_driver_score_data::getDaily_driver_score)
                .average()
                .orElse(0.0);



        return average;
    }

}
