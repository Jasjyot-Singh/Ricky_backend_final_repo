package com.example.ricky_backend_final.controller;

import com.example.ricky_backend_final.entity.Mldata;
import com.example.ricky_backend_final.repository.MLDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ml-data")
public class MLDataBackendController {

    @Autowired
    private MLDataRepository repository;

    // CREATE - add single predicted data
    @PostMapping("/add")
    public Mldata addData(@RequestBody Mldata data) {
        return repository.save(data);
    }

    // CREATE - add multiple predicted data
    @PostMapping("/add-batch")
    public List<Mldata> addBatch(@RequestBody List<Mldata> dataList) {
        return repository.saveAll(dataList);
    }

    // READ - get all data
    @GetMapping("/all")
    public List<Mldata> getAllData() {
        return repository.findAll();
    }

    // READ - get by ID
    @GetMapping("/{id}")
    public Mldata getById(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    }

    // UPDATE
    @PutMapping("/update/{id}")
    public Mldata updateData(@PathVariable Long id, @RequestBody Mldata newData) {
        return repository.findById(id).map(data -> {
            data.setLat(newData.getLat());
            data.setLon(newData.getLon());
            data.setPotholeLabel(newData.getPotholeLabel());
            data.setConfidence(newData.getConfidence());
            return repository.save(data);
        }).orElse(null);
    }

    // DELETE
    @DeleteMapping("/delete/{id}")
    public String deleteData(@PathVariable Long id) {
        repository.deleteById(id);
        return "Deleted record with ID " + id;
    }
}
