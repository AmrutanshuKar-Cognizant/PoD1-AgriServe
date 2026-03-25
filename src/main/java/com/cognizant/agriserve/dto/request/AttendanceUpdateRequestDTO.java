package com.cognizant.agriserve.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceUpdateRequestDTO {

    @NotNull(message = "Participation ID is mandatory to update attendance")
    private Long participationId;

    @NotBlank(message = "New attendance status (e.g., Present, Absent) cannot be empty")
    private String newAttendanceStatus;
}