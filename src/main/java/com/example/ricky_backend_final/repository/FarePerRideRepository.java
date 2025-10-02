package com.example.ricky_backend_final.repository;

import com.example.ricky_backend_final.entity.FarePerRideData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FarePerRideRepository extends JpaRepository<FarePerRideData, String> {
    List<FarePerRideData> findByReceivedAtAfterOrderByReceivedAtDesc(LocalDateTime dateTime);
    List<FarePerRideData> findByDriverIdOrderByReceivedAtDesc(String driverId);
}
