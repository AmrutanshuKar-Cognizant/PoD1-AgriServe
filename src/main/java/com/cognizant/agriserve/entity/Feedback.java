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
    @JoinColumn(name = "sessionId")
    private AdvisorySession session;

    @ManyToOne
    @JoinColumn(name = "farmerId")
    private Farmer farmer;

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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    private Integer rating; // Usually 1-5

    @Column(columnDefinition = "TEXT")
    private String comments;

    private LocalDateTime date;

}
