package com.cognizant.agriserve.dto;

public class AdvisorySessionRequestDTO {
    private Integer farmerId;
    private Integer contentId;
    private String feedback; // These are the Officer's consultation notes

    // Getters and Setters
    public Integer getFarmerId() { return farmerId; }
    public void setFarmerId(Integer farmerId) { this.farmerId = farmerId; }

    public Integer getContentId() { return contentId; }
    public void setContentId(Integer contentId) { this.contentId = contentId; }

    public String getFeedback() { return feedback; }
    public void setFeedback(String feedback) { this.feedback = feedback; }
}