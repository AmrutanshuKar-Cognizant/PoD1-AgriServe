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

//    public ContentUsageReportDTO(String title, Long usageCount) {
//        this.title = title;
//        this.usageCount = usageCount;
//    }
//
//    // Getters
//    public String getTitle() { return title; }
//    public Long getUsageCount() { return usageCount; }
}