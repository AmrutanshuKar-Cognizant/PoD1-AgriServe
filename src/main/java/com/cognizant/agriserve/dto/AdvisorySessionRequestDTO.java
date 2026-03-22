package com.cognizant.agriserve.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdvisorySessionRequestDTO {
    private Long farmerId;
    private Long contentId;
    private String feedback; // These are the Officer's consultation notes

    // Getters and Setters
//    public Integer getFarmerId() { return farmerId; }
//    public void setFarmerId(Integer farmerId) { this.farmerId = farmerId; }
//
//    public Integer getContentId() { return contentId; }
//    public void setContentId(Integer contentId) { this.contentId = contentId; }
//
//    public String getFeedback() { return feedback; }
//    public void setFeedback(String feedback) { this.feedback = feedback; }
}