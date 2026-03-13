package com.cognizant.agriserve.entity;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "satisfaction_metrics")
public class SatisfactionMetric {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer metricId;

    @ManyToOne
    @JoinColumn(name = "ProgramId")
    private TrainingProgram program;

    @ManyToOne
    @JoinColumn(name = "OfficerID")
    private Farmer farmer;

    private BigDecimal score;
    private LocalDate date;
    private String status;

    // Getters and Setters
}