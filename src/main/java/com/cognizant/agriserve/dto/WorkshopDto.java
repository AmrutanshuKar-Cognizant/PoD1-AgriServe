package com.cognizant.agriserve.dto;

import java.time.LocalDateTime;

public class WorkshopDto {

    private Long workshopId;
    private String programTitle;
    private Long officerId;
    private String location;
    private LocalDateTime date;
    private String status;

    public WorkshopDto() {
    }

    public WorkshopDto(Long workshopId, String programTitle, Long officerId,
                       String location, LocalDateTime date, String status) {
        this.workshopId = workshopId;
        this.programTitle = programTitle;
        this.officerId = officerId;
        this.location = location;
        this.date = date;
        this.status = status;
    }

    public Long getWorkshopId() { return workshopId; }
    public void setWorkshopId(Long workshopId) { this.workshopId = workshopId; }

    public String getProgramTitle() { return programTitle; }
    public void setProgramTitle(String programTitle) { this.programTitle = programTitle; }

    public Long getOfficerId() { return officerId; }
    public void setOfficerId(Long officerId) { this.officerId = officerId; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}