package com.cognizant.agriserve.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComplianceOfficerDashboardDTO {

    private Long totalTrainingPrograms;
    private Long ongoingTrainingPrograms;

    private Long totalAdvisorySessions;

    private Long pendingAudits;
    private Long ongoingAudits;
    private Long completedAudits;

}