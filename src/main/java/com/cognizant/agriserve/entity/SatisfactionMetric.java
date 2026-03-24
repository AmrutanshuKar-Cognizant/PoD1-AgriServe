package com.cognizant.agriserve.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "satisfactionMetrics")
public class SatisfactionMetric {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long metricId;

    @OneToOne
    @JoinColumn(name = "programID")
    private TrainingProgram trainingProgram ;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User programManager;

    private Double score;
    private LocalDate date;
    private String status;

    // Standard Getters/Setters
    public Long getMetricId() {
        return metricId;
    }
    public void setMetricId(Long metricId) {
        this.metricId = metricId;
    }
    public TrainingProgram  getTrainingProgram () {
        return trainigProgram;
    }
    public void setTrainingProgram (TrainingProgram  trainingProgram ) {
        this.trainingProgram = trainingProgram;
    }
    public User getProgramManager() {
        return programManager;
    }
    public void setProgramManager(User programManager) {
        this.programManager = programManager;
    }
    public Double getScore() {
        return score;
    }
    public void setScore(Double score) {
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
}