package com.cognizant.agriserve.entity;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "satisfactionMetrics")
public class SatisfactionMetric {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer metricId;

    @ManyToOne
    @JoinColumn(name = "programId")
    private TrainingProgram program;

    public Integer getMetricId() {
        return metricId;
    }

    public void setMetricId(Integer metricId) {
        this.metricId = metricId;
    }

    public TrainingProgram getProgram() {
        return program;
    }

    public void setProgram(TrainingProgram program) {
        this.program = program;
    }

    public Farmer getFarmer() {
        return farmer;
    }

    public void setFarmer(Farmer farmer) {
        this.farmer = farmer;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @ManyToOne
    @JoinColumn(name = "officerID")
    private Farmer farmer;

    private BigDecimal score;
    private LocalDate date;
    private String status;

}