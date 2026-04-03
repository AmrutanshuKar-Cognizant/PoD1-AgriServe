package com.cognizant.agriserve.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParticipationDTO {

    private Long participationId;

    @NotNull(message = "Workshop ID is mandatory")
    private Long workshopId;

    private Long farmerId;

    private String attendanceStatus;
    private String feedback;
}