package com.cognizant.agriserve.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "participation")
public class Participation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long participationId;

    @ManyToOne
    @JoinColumn(name = "workshopId")
    private Workshop workshop;

    private Long farmerId;
    private String attendanceStatus;
    private String feedback;

    public Participation() {}

    // Getters and Setters
    public Long getParticipationId() { return participationId; }
    public void setParticipationId(Long participationId) { this.participationId = participationId; }

    public Workshop getWorkshop() { return workshop; }
    public void setWorkshop(Workshop workshop) { this.workshop = workshop; }

    public Long getFarmerId() { return farmerId; }
    public void setFarmerId(Long farmerId) { this.farmerId = farmerId; }

    public String getAttendanceStatus() { return attendanceStatus; }
    public void setAttendanceStatus(String attendanceStatus) { this.attendanceStatus = attendanceStatus; }

    public String getFeedback() { return feedback; }
    public void setFeedback(String feedback) { this.feedback = feedback; }
}