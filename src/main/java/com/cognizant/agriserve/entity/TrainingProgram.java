package com.cognizant.agriserve.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "trainingProgram")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainingProgram {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long programId;

    @Column(nullable = false)
    private String title;

    private String description;

    private LocalDate startDate; // Using java.time for modern Java versions
    private LocalDate endDate;

    private String status;

    @OneToMany(mappedBy = "trainingProgram", cascade = CascadeType.ALL)
    private List<Workshop> workshops;

}