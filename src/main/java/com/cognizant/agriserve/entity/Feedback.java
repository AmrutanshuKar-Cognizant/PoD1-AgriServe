package com.cognizant.agriserve.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "feedback")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedbackId;

    @OneToOne
    @JoinColumn(name = "sessionId")
    private AdvisorySession session;

    @ManyToOne
    @JoinColumn(name = "farmerId")
    private Farmer farmer;

    @ManyToOne
    @JoinColumn(name="programId")
    private TrainingProgram trainingProgram;

    private Integer rating;
    @Column(columnDefinition = "TEXT")
    private String comments;
    private LocalDate date;
}