package com.example.ricky_backend_final.service;


import com.example.ricky_backend_final.entity.Acceloremeter_data_class;
import com.example.ricky_backend_final.repository.Accelerometer_data_repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class Accelerometer_data_service {

    private final Accelerometer_data_repository accelRepo;

    public Accelerometer_data_service(Accelerometer_data_repository accelRepo) {
        this.accelRepo = accelRepo;
    }

    public Acceloremeter_data_class saveData(Acceloremeter_data_class data) {
        return accelRepo.save(data);
    }



    public List<Acceloremeter_data_class> getAll() {
        return accelRepo.findAll();
    }



    public void delete(Acceloremeter_data_class data) {
        accelRepo.delete(data);
    }

    public List<Acceloremeter_data_class> getByDriverId(String driverId) {
        return accelRepo.findAllByDriverId(driverId);
    }

    public List<Acceloremeter_data_class> getMlData(String driverId) {
        return accelRepo.getMlData(driverId);
    }

    @Transactional
    public void saveAll(List<Acceloremeter_data_class> dataList) {
        accelRepo.saveAll(dataList);
    }
}

