package com.example.ricky_backend_final.repository;

import com.example.ricky_backend_final.entity.Acceloremeter_data_class;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Accelerometer_data_repository extends JpaRepository<Acceloremeter_data_class, Integer> {

    List<Acceloremeter_data_class> findAllByDriverId(String driverId);


    @Query("SELECT a FROM Acceloremeter_data_class a WHERE a.driverId = :driverid")
    List<Acceloremeter_data_class> getMlData(@Param("driverid") String driverid);

}
