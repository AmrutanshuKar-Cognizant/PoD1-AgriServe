package com.cognizant.agriserve.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
