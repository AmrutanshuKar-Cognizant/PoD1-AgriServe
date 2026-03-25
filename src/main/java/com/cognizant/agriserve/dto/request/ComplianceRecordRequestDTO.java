package com.cognizant.agriserve.dto.request;

import com.cognizant.agriserve.entity.ComplianceRecord.ComplianceType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComplianceRecordRequestDTO {

    @NotNull(message = "Entity ID is required")
    @Positive(message = "Entity ID must be a valid positive number")
    private Long entityId;

    @NotNull(message = "Compliance type is required")
    private ComplianceType type;

    @NotBlank(message = "Result cannot be blank (e.g., PASSED or FAILED)")
    private String result;

    @NotBlank(message = "Notes are required to explain the result")
    @Size(max = 500, message = "Notes cannot exceed 500 characters")
    private String notes;


}