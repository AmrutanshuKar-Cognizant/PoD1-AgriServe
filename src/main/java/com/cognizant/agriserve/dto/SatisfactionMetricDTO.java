package com.cognizant.agriserve.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SatisfactionMetricDTO {
    @NotNull(message = "Feedback ID is required")
    private Long programId;

    @NotNull(message = "Officer ID is required")
    private Long officeId;

    private String status;

}