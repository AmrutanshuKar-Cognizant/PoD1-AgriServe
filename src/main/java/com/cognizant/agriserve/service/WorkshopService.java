package com.cognizant.agriserve.service;

import com.cognizant.agriserve.dto.WorkshopDto;
import java.util.List;

/**
 * Service interface for Workshop management operations.
 */
public interface WorkshopService {

    /**
     * Retrieves all workshops in the system.
     * @return List of WorkshopDto
     */
    List<WorkshopDto> getAllWorkshops();

    /**
     * Retrieves only active workshops (Scheduled or Ongoing) suitable for farmer viewing.
     * @return List of WorkshopDto
     */
    List<WorkshopDto> getActiveWorkshopsForFarmers();
    // ... your existing methods ...

    /**
     * Creates and schedules a new workshop in the database.
     */
    WorkshopDto scheduleWorkshop(WorkshopDto workshopDto);

    /**
     * Fetches the schedule for a specific Extension Officer.
     */
    List<WorkshopDto> getWorkshopsByOfficer(Long officerId);

    /**
     * Updates the status of a specific workshop (e.g., Scheduled -> Ongoing).
     */
    WorkshopDto updateWorkshopStatus(Long workshopId, String status);
}
