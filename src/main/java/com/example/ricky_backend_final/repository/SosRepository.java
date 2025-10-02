package com.example.ricky_backend_final.repository;

import com.example.ricky_backend_final.entity.SosAlert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SosRepository extends JpaRepository<SosAlert, String> {
    List<SosAlert> findByStatusOrderByTimestampDesc(String status);
    List<SosAlert> findByDriverIdOrderByTimestampDesc(String driverId);
    List<SosAlert> findByAcknowledgedFalseOrderByTimestampDesc();
}
