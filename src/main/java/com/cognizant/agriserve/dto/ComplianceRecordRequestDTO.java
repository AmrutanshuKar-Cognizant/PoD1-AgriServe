package com.cognizant.agriserve.dto;

import com.cognizant.agriserve.entity.ComplianceRecord.ComplianceType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

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

    public ComplianceRecordRequestDTO() {
    }

    public ComplianceRecordRequestDTO(Long entityId, ComplianceType type, String result, String notes) {
        this.entityId = entityId;
        this.type = type;
        this.result = result;
        this.notes = notes;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public ComplianceType getType() {
        return type;
    }

    public void setType(ComplianceType type) {
        this.type = type;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}