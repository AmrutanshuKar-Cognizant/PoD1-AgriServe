package com.cognizant.agriserve.service;

import com.cognizant.agriserve.dto.AttendanceUpdateRequestDTO;
import com.cognizant.agriserve.dto.ParticipationDTO;
import java.util.List;

/**
 * Service interface handling farmer workshop registrations and attendance tracking.
 */
public interface ParticipationService {

    /**
     * Registers a farmer for a specific workshop.
     *
     * @param dto The DTO containing the workshop ID and farmer ID.
     * @return The saved ParticipationDto with an initial status of "Registered".
     * @throws com.cognizant.agriserve.exception.ResourceConflictException if the farmer is already registered for the workshop.
     */
    ParticipationDTO registerForWorkshop(ParticipationDTO dto);

    /**
     * Fetches all registered participants for a specific workshop instance.
     *
     * @param workshopId The unique identifier of the workshop.
     * @return List of ParticipationDto representing the attendance roster.
     */
    List<ParticipationDTO> getParticipantsForWorkshop(Long workshopId);

    /**
     * Updates the attendance status (e.g., Present, Absent) for a specific participation record.
     *
     * @param requestDto The DTO containing the participation ID and the new status.
     * @return The updated ParticipationDto.
     * @throws com.cognizant.agriserve.exception.ResourceNotFoundException if the record does not exist.
     */
    ParticipationDTO updateAttendance(AttendanceUpdateRequestDTO requestDto);
    List<ParticipationDTO> getParticipationByFarmerId(Long farmerId);
}