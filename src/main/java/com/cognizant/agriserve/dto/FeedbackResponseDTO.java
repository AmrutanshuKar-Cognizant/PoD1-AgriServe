package com.cognizant.agriserve.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackResponseDTO {
    private Long FeedbackId;
    private String FarmerName;
    private String programName;
    private int rating;
    private String Comments;
}
