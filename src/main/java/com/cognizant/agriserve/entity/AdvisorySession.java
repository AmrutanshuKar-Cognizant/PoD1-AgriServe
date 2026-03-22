package com.cognizant.agriserve.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "advisorySession")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdvisorySession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer sessionId;

    @Column(name = "OfficerID")
    private Integer officerId;

    @Column(name = "FarmerID")
    private Integer farmerId;

    @ManyToOne
    @JoinColumn(name = "ContentID")
    private AdvisoryContent content;

    private LocalDateTime date = LocalDateTime.now();
    private String status = "Completed";

    @Column(columnDefinition = "TEXT")
    private String feedback;


}