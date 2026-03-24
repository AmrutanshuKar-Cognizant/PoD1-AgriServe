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
@Table(name = "complianceRecord")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComplianceRecord {
    public enum ComplianceType {
        ADVISORY,
        TRAINING
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long complianceId;

    // Polymorphic Foreign Key (Can be SessionID or ProgramID)
    @Column(nullable = false)
    private Long entityId;

    @Column(nullable = false)
    private Long officerId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ComplianceType type;

    @Column(nullable = false)
    private String result;

    @Column(nullable = false)
    private LocalDateTime date;

    private String notes;


    // Automatically set the date to the current server time if not provided
    @PrePersist
    protected void onCreate() {
        if (this.date == null) {
            this.date = LocalDateTime.now();
        }
    }

}
