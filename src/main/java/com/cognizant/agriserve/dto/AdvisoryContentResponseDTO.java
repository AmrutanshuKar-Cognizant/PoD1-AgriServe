package com.cognizant.agriserve.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AdvisoryContentResponseDTO {
    private Long contentId;
    private String title;
    private String category;
    private String fileUri;
    private String description;
    private String status;
}