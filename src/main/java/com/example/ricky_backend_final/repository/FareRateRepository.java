package com.example.ricky_backend_final.repository;

import com.example.ricky_backend_final.entity.FareRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FareRateRepository extends JpaRepository<FareRate, Long> {
    Optional<FareRate> findTopByActiveOrderByUpdatedAtDesc(boolean active);
}
