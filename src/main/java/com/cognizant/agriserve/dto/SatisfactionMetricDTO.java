package com.cognizant.agriserve.dto;

import jakarta.validation.constraints.NotNull;

public class SatisfactionMetricDTO {
    @NotNull(message = "Feedback ID is required")
    private Long programId;

    @NotNull(message = "Officer ID is required")
    private Long officeId;

    private String status;

    // Getters and Setters
    public Long getProgramId() {
        return programId;
    }
    public void setProgramId(Integer programId) {
        this.programId = programId;
    }
    public Long getOfficeId() {
        return officeId;
    }
    public void setOfficeId(Long officeId) {
        this.officeId = officeId;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}