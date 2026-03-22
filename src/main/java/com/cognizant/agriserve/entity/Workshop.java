package com.cognizant.agriserve.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "workshop")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Workshop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long workshopId;

    @ManyToOne
    @JoinColumn(name = "programId")
    private TrainingProgram trainingProgram;

    private Long officerId;
    private String location;

    private LocalDateTime date; // Modern LocalDateTime replaces java.util.Date

    private String status;

    @OneToMany(mappedBy = "workshop", cascade = CascadeType.ALL)
    private List<Participation> participations;

}