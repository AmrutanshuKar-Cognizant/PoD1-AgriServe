package com.cognizant.agriserve.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "advisoryContent")
public class AdvisoryContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contentId;
    @NotBlank(message = "Title is mandatory")
    @Column(nullable = false)
    private String title;
    @NotBlank(message = "Category is mandatory")

    @ManyToOne
    @JoinColumn(name ="uploaded_By",referencedColumnName = "userId")
    private User uploaded_By;
    private String category;
    private String fileUri;
    private String description;
    private LocalDateTime uploadedDate = LocalDateTime.now();
    private String status = "Active";

}