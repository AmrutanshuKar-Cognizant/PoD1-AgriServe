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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;



@Entity
@Table(name = "audit")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Audit {
    public enum AuditStatus {
        PENDING,
        IN_PROGRESS,
        COMPLETED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long auditId;

    // Links to the User table (the Compliance Officer who created it)
    @Column(nullable = false)
    private Long officerId;

    // What is being audited (e.g., "Q3 Northern Region Workshops")
    @Column(nullable = false, length = 255)
    private String scope;

    // The detailed report/result of the investigation
    @Column(columnDefinition = "TEXT")
    private String findings;

    @Column(nullable = false)
    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuditStatus status;


    // Automatically set the audit date to current server time if not provided
    @PrePersist
    protected void onCreate() {
        if (this.date == null) {
            this.date = LocalDateTime.now();
        }
    }

}
