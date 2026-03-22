package com.cognizant.agriserve.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "advisorySession")
public class AdvisorySession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sessionId;

    @ManyToOne
    @JoinColumn(name = "officer_id") // Assuming User has userId
    private User officer;

    @ManyToOne
    @JoinColumn(name = "farmer_id") // Assuming Farmer has farmerId
    private Farmer farmer;

    @ManyToOne
    @JoinColumn(name = "ContentID")
    private AdvisoryContent content;

    private LocalDateTime date = LocalDateTime.now();
    private String status = "Completed";

    @Column(columnDefinition = "TEXT")
    private String feedback;

    // Default Constructor
//    public AdvisorySession() {}
//
//    // Getters and Setters
//    public Integer getSessionId() { return sessionId; }
//    public void setSessionId(Integer sessionId) { this.sessionId = sessionId; }
//
//    public Integer getOfficerId() { return officerId; }
//    public void setOfficerId(Integer officerId) { this.officerId = officerId; }
//
//    public Integer getFarmerId() { return farmerId; }
//    public void setFarmerId(Integer farmerId) { this.farmerId = farmerId; }
//
//    public AdvisoryContent getContent() { return content; }
//    public void setContent(AdvisoryContent content) { this.content = content; }
//
//    public LocalDateTime getDate() { return date; }
//    public void setDate(LocalDateTime date) { this.date = date; }
//
//    public String getStatus() { return status; }
//    public void setStatus(String status) { this.status = status; }
//
//    public String getFeedback() { return feedback; }
//    public void setFeedback(String feedback) { this.feedback = feedback; }
}