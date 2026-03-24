package com.cognizant.agriserve.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContentUsageReportDTO {
    private String title;
    private Long usageCount;
}