package com.cognizant.agriserve.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "advisoryContent")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdvisoryContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer contentId;

    private String title;
    private String category;
    private String fileUri;
    private String description;
    private LocalDateTime uploadedDate = LocalDateTime.now();
    private String status = "Active";

}