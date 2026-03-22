package com.cognizant.agriserve.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class FeedbackDTO {
    @NotNull(message = "Session ID is mandatory")
    private Integer sessionId;

    @NotNull(message = "Farmer ID is mandatory")
    private Integer farmerId;

    @NotNull(message = "Program ID is mandatory")
    private Integer programId;

    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating cannot exceed 5")
    private int rating;

    @NotBlank(message = "Comment cannot be empty")
    private String comment;

    // Getters and Setters
    public Integer getSessionId() {
        return sessionId;
    }
    public void setSessionId(Integer sessionId) {
        this.sessionId = sessionId;
    }
    public Integer getFarmerId() {
        return farmerId;
    }
    public void setFarmerId(Integer farmerId) {
        this.farmerId = farmerId;
    }
    public Integer getProgramId() {
        return programId;
    }
    public void setProgramId(Integer programId) {
        this.programId = programId;
    }
    public int getRating() {
        return rating;
    }
    public void setRating(int rating) {
        this.rating = rating;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
}