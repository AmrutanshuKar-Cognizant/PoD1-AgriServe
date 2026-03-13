package com.cognizant.agriserve.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "feedback")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer feedbackId;

    @OneToOne
    @JoinColumn(name = "session_id")
    private AdvisorySession session;

    @ManyToOne
    @JoinColumn(name = "farmer_id")
    private Farmer farmer;

    private Integer rating; // Usually 1-5

    @Column(columnDefinition = "TEXT")
    private String comments;

    private LocalDateTime date;

    // Getters and Setters
}
