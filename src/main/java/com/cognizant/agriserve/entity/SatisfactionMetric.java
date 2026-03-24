package com.cognizant.agriserve.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "satisfactionMetrics")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SatisfactionMetric {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long metricId;

    @OneToOne
    @JoinColumn(name = "programId")
    private TrainingProgram trainingProgram ;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User programManager;

    private Double score;
    private LocalDate date;
    private String status;


}