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

enum AuditStatus {
    PENDING,
    IN_PROGRESS,
    COMPLETED
}

@Entity
@Table(name = "audit")
public class Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "audit_id")
    private Long auditId;

    // Links to the User table (the Compliance Officer who created it)
    @Column(name = "officer_id", nullable = false)
    private Long officerId;

    // What is being audited (e.g., "Q3 Northern Region Workshops")
    @Column(name = "scope", nullable = false, length = 255)
    private String scope;

    // The detailed report/result of the investigation
    @Column(name = "findings", columnDefinition = "TEXT")
    private String findings;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private AuditStatus status;

    // Default Constructor
    public Audit() {
    }

    // Parameterized Constructor
    public Audit(Long officerId, String scope, String findings, AuditStatus status) {
        this.officerId = officerId;
        this.scope = scope;
        this.findings = findings;
        this.status = status;
    }

    // Automatically set the audit date to current server time if not provided
    @PrePersist
    protected void onCreate() {
        if (this.date == null) {
            this.date = LocalDateTime.now();
        }
    }

    public Long getAuditId() {
        return auditId;
    }

    public void setAuditId(Long auditId) {
        this.auditId = auditId;
    }

    public Long getOfficerId() {
        return officerId;
    }

    public void setOfficerId(Long officerId) {
        this.officerId = officerId;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getFindings() {
        return findings;
    }

    public void setFindings(String findings) {
        this.findings = findings;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public AuditStatus getStatus() {
        return status;
    }

    public void setStatus(AuditStatus status) {
        this.status = status;
    }
}
