package com.cognizant.agriserve.dto;

import lombok.Data;

@Data
public class SatisfactionMetricResponseDTO {
 private Long programId;
 private String status;
 private Double score;
}
