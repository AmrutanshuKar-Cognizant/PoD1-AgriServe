package com.cognizant.agriserve.dto;

import com.cognizant.agriserve.entity.Audit.AuditStatus;
import java.time.LocalDateTime;

public class AuditResponseDTO {

    private Long auditId;
    private Long officerId;
    private String officerName;
    private String scope;
    private String findings;
    private LocalDateTime date;
    private AuditStatus status;

    public AuditResponseDTO() {
    }

    public AuditResponseDTO(Long auditId, Long officerId, String officerName, String scope, String findings, LocalDateTime date, AuditStatus status) {
        this.auditId = auditId;
        this.officerId = officerId;
        this.officerName = officerName;
        this.scope = scope;
        this.findings = findings;
        this.date = date;
        this.status = status;
    }

    public Long getAuditId() {
        return auditId;
    }

    public void setAuditId(Long auditId) {
        this.auditId = auditId;
    }

    public Long getOfficerId() {
        return officerId;
    }

    public void setOfficerId(Long officerId) {
        this.officerId = officerId;
    }

    public String getOfficerName() {
        return officerName;
    }

    public void setOfficerName(String officerName) {
        this.officerName = officerName;
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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public AuditStatus getStatus() {
        return status;
    }

    public void setStatus(AuditStatus status) {
        this.status = status;
    }
}