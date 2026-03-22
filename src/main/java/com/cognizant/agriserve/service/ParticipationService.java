package com.cognizant.agriserve.service;

import com.cognizant.agriserve.dto.AttendanceUpdateRequestDto;
import com.cognizant.agriserve.dto.ParticipationDto;
import java.util.List;

/**
 * Service interface handling farmer workshop registrations and attendance tracking.
 */
public interface ParticipationService {

    /**
     * Fetches all registered participants for a specific workshop instance.
     *
     * @param workshopId The unique identifier of the workshop.
     * @return List of ParticipationDto representing the attendance roster.
     */
    List<ParticipationDto> getParticipantsForWorkshop(Long workshopId);

    /**
     * Updates the attendance status (e.g., Present, Absent) for a specific participation record.
     *
     * @param requestDto The DTO containing the participation ID and the new status.
     * @return The updated ParticipationDto.
     * @throws RuntimeException if the record does not exist.
     */
    ParticipationDto updateAttendance(AttendanceUpdateRequestDto requestDto);
}