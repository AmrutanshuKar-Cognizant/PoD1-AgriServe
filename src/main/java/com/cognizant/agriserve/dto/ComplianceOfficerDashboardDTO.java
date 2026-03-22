package com.cognizant.agriserve.dto;

public class ComplianceOfficerDashboardDTO {

    private Long totalTrainingPrograms;
    private Long ongoingTrainingPrograms;

    private Long totalAdvisorySessions;

    private Long pendingAudits;
    private Long ongoingAudits;
    private Long completedAudits;

    public ComplianceOfficerDashboardDTO() {
    }

    public ComplianceOfficerDashboardDTO(Long totalTrainingPrograms, Long ongoingTrainingPrograms,
                                         Long totalAdvisorySessions, Long pendingAudits,
                                         Long ongoingAudits, Long completedAudits) {
        this.totalTrainingPrograms = totalTrainingPrograms;
        this.ongoingTrainingPrograms = ongoingTrainingPrograms;
        this.totalAdvisorySessions = totalAdvisorySessions;
        this.pendingAudits = pendingAudits;
        this.ongoingAudits = ongoingAudits;
        this.completedAudits = completedAudits;
    }

    public Long getTotalTrainingPrograms() {
        return totalTrainingPrograms;
    }

    public void setTotalTrainingPrograms(Long totalTrainingPrograms) {
        this.totalTrainingPrograms = totalTrainingPrograms;
    }

    public Long getOngoingTrainingPrograms() {
        return ongoingTrainingPrograms;
    }

    public void setOngoingTrainingPrograms(Long ongoingTrainingPrograms) {
        this.ongoingTrainingPrograms = ongoingTrainingPrograms;
    }

    public Long getTotalAdvisorySessions() {
        return totalAdvisorySessions;
    }

    public void setTotalAdvisorySessions(Long totalAdvisorySessions) {
        this.totalAdvisorySessions = totalAdvisorySessions;
    }

    public Long getPendingAudits() {
        return pendingAudits;
    }

    public void setPendingAudits(Long pendingAudits) {
        this.pendingAudits = pendingAudits;
    }

    public Long getOngoingAudits() {
        return ongoingAudits;
    }

    public void setOngoingAudits(Long ongoingAudits) {
        this.ongoingAudits = ongoingAudits;
    }

    public Long getCompletedAudits() {
        return completedAudits;
    }

    public void setCompletedAudits(Long completedAudits) {
        this.completedAudits = completedAudits;
    }
}