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
    @NotBlank(message = "Title is mandatory")
    @Column(nullable = false)
    private String category;
    private String fileUri;
    private String description;
    private LocalDateTime uploadedDate = LocalDateTime.now();
    private String status = "Active";

    // Default Constructor
//    public AdvisoryContent() {}
//
//    // Getters and Setters
//    public Integer getContentId() { return contentId; }
//    public void setContentId(Integer contentId) { this.contentId = contentId; }
//
//    public String getTitle() { return title; }
//    public void setTitle(String title) { this.title = title; }
//
//    public String getCategory() { return category; }
//    public void setCategory(String category) { this.category = category; }
//
//    public String getFileUri() { return fileUri; }
//    public void setFileUri(String fileUri) { this.fileUri = fileUri; }
//
//    public String getDescription() { return description; }
//    public void setDescription(String description) { this.description = description; }
//
//    public LocalDateTime getUploadedDate() { return uploadedDate; }
//    public void setUploadedDate(LocalDateTime uploadedDate) { this.uploadedDate = uploadedDate; }
//
//    public String getStatus() { return status; }
//    public void setStatus(String status) { this.status = status; }
}