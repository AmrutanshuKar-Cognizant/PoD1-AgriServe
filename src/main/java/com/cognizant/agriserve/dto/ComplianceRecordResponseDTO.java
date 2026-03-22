package com.cognizant.agriserve.dto;

import com.cognizant.agriserve.entity.ComplianceRecord.ComplianceType;
import java.time.LocalDateTime;

public class ComplianceRecordResponseDTO {

    private Long complianceId;
    private Long entityId;
    private ComplianceType type;
    private Long officerId;
    private String result;
    private LocalDateTime date;
    private String notes;

    public ComplianceRecordResponseDTO() {
    }

    public ComplianceRecordResponseDTO(Long complianceId, Long entityId, ComplianceType type, String result, LocalDateTime date, String notes) {
        this.complianceId = complianceId;
        this.entityId = entityId;
        this.type = type;
        this.officerId = officerId;
        this.result = result;
        this.date = date;
        this.notes = notes;
    }

    public Long getComplianceId() {
        return complianceId;
    }

    public void setComplianceId(Long complianceId) {
        this.complianceId = complianceId;
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

    public Long getOfficerId() { return officerId; }

    public void setOfficerId(Long officerId) { this.officerId = officerId; }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}