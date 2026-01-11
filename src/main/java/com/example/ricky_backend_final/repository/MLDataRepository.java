package com.example.ricky_backend_final.repository;

import com.example.ricky_backend_final.entity.Mldata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MLDataRepository extends JpaRepository<Mldata, Long> {}

