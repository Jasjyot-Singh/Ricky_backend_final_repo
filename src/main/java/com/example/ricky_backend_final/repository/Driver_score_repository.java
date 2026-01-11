package com.example.ricky_backend_final.repository;

import com.example.ricky_backend_final.entity.Daily_driver_score_data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface Driver_score_repository extends JpaRepository<Daily_driver_score_data, String> {
    List<Daily_driver_score_data> findByDriverDriverId(String driverId);


    @Query("SELECT d FROM Daily_driver_score_data d WHERE d.driver.licenseNumber = :license")
    List<Daily_driver_score_data> findByDriverLicenseNumber(@Param("license") String license);


    @Query("SELECT d FROM Daily_driver_score_data d WHERE d.driver.driverId = :driverId AND d.date BETWEEN :start AND :end")
    List<Daily_driver_score_data> findByDriverIdAndDateBetween(
            @Param("driverId") String driverId,
            @Param("start") LocalDate start,
            @Param("end") LocalDate end
    );
}
