package com.cognizant.agriserve.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

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

    @NotNull(message = "Officer is required")
    @ManyToOne(optional = false)
    @JoinColumn(name = "officer_id", nullable = false) // Assuming User has userId
    private User officer;

    @NotNull(message = "Farmer is required")
    @ManyToOne(optional = false)//It tells Hibernate, "This relationship is mandatory." If you try to save an AdvisorySession without a Farmer, Hibernate will catch it before even trying to talk to the database.
    @JoinColumn(name = "farmer_id", nullable = false)// Assuming Farmer has farmerId
    private Farmer farmer;

    @NotNull(message = "Advisory content is required")
    @ManyToOne(optional = false)
    @JoinColumn(name = "content_id", nullable = false)
    private AdvisoryContent content;

    private LocalDateTime date = LocalDateTime.now();
    private String status = "Completed";

    @NotBlank(message = "Consultation feedback is required")
    @Column(columnDefinition = "TEXT", nullable = false)
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