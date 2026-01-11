package com.example.ricky_backend_final.controller;

import com.example.ricky_backend_final.service.Accelerometer_data_service;
import com.example.ricky_backend_final.entity.Acceloremeter_data_class;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accelerometer")
public class Accelerometer_data_controller {

    private final Accelerometer_data_service accelService;


    public Accelerometer_data_controller(Accelerometer_data_service accelService) {
        this.accelService = accelService;
    }

    @PostMapping
    public ResponseEntity<Acceloremeter_data_class> createData(@RequestBody Acceloremeter_data_class data) {
        return ResponseEntity.ok(accelService.saveData(data));
    }

    @GetMapping
    public ResponseEntity<List<Acceloremeter_data_class>> getAll() {
        return ResponseEntity.ok(accelService.getAll());
    }


    @GetMapping("/driver/{driverId}")
    public ResponseEntity<List<Acceloremeter_data_class>> getByDriverId(@PathVariable String driverId) {
        return ResponseEntity.ok(accelService.getByDriverId(driverId));
    }

    @GetMapping("/ml/{driverId}")
    public ResponseEntity<List<Acceloremeter_data_class>> getMlData(@PathVariable String driverId) {
        return ResponseEntity.ok(accelService.getMlData(driverId));
    }

    @PostMapping("/batch")
    public ResponseEntity<Void> saveAll(@RequestBody List<Acceloremeter_data_class> dataList) {
        accelService.saveAll(dataList);
        return ResponseEntity.ok().build();
    }
}

