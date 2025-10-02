package com.example.ricky_backend_final.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "fare_rates")
public class FareRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double rate;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private Boolean active = true;

    // Getters and Setters ...

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Double getRate() { return rate; }
    public void setRate(Double rate) { this.rate = rate; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public String getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(String updatedBy) { this.updatedBy = updatedBy; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
}
