package com.cognizant.agriserve.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.PrePersist;
import java.time.LocalDateTime;

enum ComplianceType {
    ADVISORY,
    TRAINING
}

@Entity
@Table(name = "compliance_record")
public class ComplianceRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "compliance_id")
    private Long complianceId;

    // Polymorphic Foreign Key (Can be SessionID or ProgramID)
    @Column(name = "entity_id", nullable = false)
    private Long entityId;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ComplianceType type;

    @Column(name = "result", nullable = false)
    private String result;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Column(name = "notes")
    private String notes;

    // Default Constructor
    public ComplianceRecord() {
    }

    // Parameterized Constructor
    public ComplianceRecord(Long entityId, ComplianceType type, String result, LocalDateTime date, String notes) {
        this.entityId = entityId;
        this.type = type;
        this.result = result;
        this.date = date;
        this.notes = notes;
    }
    // Automatically set the date to the current server time if not provided
    @PrePersist
    protected void onCreate() {
        if (this.date == null) {
            this.date = LocalDateTime.now();
        }
    }

    public Long getComplianceId() {
        return complianceId;
    }

    public void setComplianceId(Long complianceId) {
        this.complianceId = complianceId;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public ComplianceType getType() {
        return type;
    }

    public void setType(ComplianceType type) {
        this.type = type;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
