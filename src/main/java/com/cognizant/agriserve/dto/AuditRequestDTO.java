package com.cognizant.agriserve.dto;

import com.cognizant.agriserve.entity.Audit.AuditStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class AuditRequestDTO {

    @NotBlank(message = "Scope cannot be blank or empty")
    @Size(min = 5, max = 100, message = "Scope must be between 5 and 100 characters")
    private String scope;

    @NotBlank(message = "Findings are required")
    private String findings;

    @NotNull(message = "Audit status is required")
    private AuditStatus status;

    public AuditRequestDTO() {
    }

    public AuditRequestDTO(Long officerId, String scope, String findings, AuditStatus status) {
        this.scope = scope;
        this.findings = findings;
        this.status = status;
    }


    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getFindings() {
        return findings;
    }

    public void setFindings(String findings) {
        this.findings = findings;
    }

    public AuditStatus getStatus() {
        return status;
    }

    public void setStatus(AuditStatus status) {
        this.status = status;
    }
}