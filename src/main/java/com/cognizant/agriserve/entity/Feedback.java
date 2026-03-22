package com.cognizant.agriserve.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "feedback")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer feedbackId;

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

    // Standard Getters/Setters
    public Integer getFeedbackId() {
        return feedbackId;
    }
    public void setFeedbackId(Integer feedbackId) {
        this.feedbackId = feedbackId;
    }
    public AdvisorySession getSession() {
        return session;
    }
    public void setSession(AdvisorySession session) {
        this.session = session;
    }
    public Farmer getFarmer() {
        return farmer;
    }
    public void setFarmer(Farmer farmer) {
        this.farmer = farmer;
    }
    public TrainingProgram getTrainingProgram() {
        return trainingProgram;
    }
    public void setTrainingProgram(TrainingProgram trainingProgram) {
        this.trainingProgram = trainingProgram;
    }
    public Integer getRating() {
        return rating;
    }
    public void setRating(Integer rating) {
        this.rating = rating;
    }
    public String getComments() {
        return comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
}