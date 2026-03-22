package com.cognizant.agriserve.dto;

public class ParticipationDto {

    private Long participationId;
    private Long workshopId;
    private Long farmerId;
    private String attendanceStatus;
    private String feedback;

    public ParticipationDto() {
    }

    public ParticipationDto(Long participationId, Long workshopId, Long farmerId,
                            String attendanceStatus, String feedback) {
        this.participationId = participationId;
        this.workshopId = workshopId;
        this.farmerId = farmerId;
        this.attendanceStatus = attendanceStatus;
        this.feedback = feedback;
    }

    public Long getParticipationId() { return participationId; }
    public void setParticipationId(Long participationId) { this.participationId = participationId; }

    public Long getWorkshopId() { return workshopId; }
    public void setWorkshopId(Long workshopId) { this.workshopId = workshopId; }

    public Long getFarmerId() { return farmerId; }
    public void setFarmerId(Long farmerId) { this.farmerId = farmerId; }

    public String getAttendanceStatus() { return attendanceStatus; }
    public void setAttendanceStatus(String attendanceStatus) { this.attendanceStatus = attendanceStatus; }

    public String getFeedback() { return feedback; }
    public void setFeedback(String feedback) { this.feedback = feedback; }
}