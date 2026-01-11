package com.example.ricky_backend_final.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "daily_driver_score_data")
public class Daily_driver_score_data {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer day_wise_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id", nullable = false) // owns the FK
    private Driver driver;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private Double daily_driver_score;

  //  private Double monthly_score;


    public Integer getDay_wise_id() { return day_wise_id; }
    public void setDay_wise_id(Integer day_wise_id) { this.day_wise_id = day_wise_id; }

    public Driver getDriver() { return driver; }
    public void setDriver(Driver driver) { this.driver = driver; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public Double getDaily_driver_score() { return daily_driver_score; }
    public void setDaily_driver_score(Double daily_driver_score) { this.daily_driver_score = daily_driver_score; }

 //   public Double getMonthly_score() { return monthly_score; }
 //   public void setMonthly_score(Double monthly_score) { this.monthly_score = monthly_score; }
}
