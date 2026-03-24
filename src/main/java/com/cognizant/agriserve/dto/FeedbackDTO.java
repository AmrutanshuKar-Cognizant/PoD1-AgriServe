package com.cognizant.agriserve.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class FeedbackDTO {
    @NotNull(message = "Session ID is mandatory")
    private Long sessionId;

    @NotNull(message = "Farmer ID is mandatory")
    private Long farmerId;

    @NotNull(message = "Program ID is mandatory")
    private Long programId;

    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating cannot exceed 5")
    private int rating;

    @NotBlank(message = "Comment cannot be empty")
    private String comment;

    // Getters and Setters
    public Long getSessionId() {
        return sessionId;
    }
    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }
    public Long getFarmerId() {
        return farmerId;
    }
    public void setFarmerId(Long farmerId) {
        this.farmerId = farmerId;
    }
    public Long getProgramId() {
        return programId;
    }
    public void setProgramId(Long programId) {
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