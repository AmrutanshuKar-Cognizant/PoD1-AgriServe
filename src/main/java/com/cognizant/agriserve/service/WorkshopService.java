package com.cognizant.agriserve.service;

import com.cognizant.agriserve.dto.WorkshopDTO;
import java.util.List;

/**
 * Service interface for Workshop management operations.
 */
public interface WorkshopService {

    /**
     * Retrieves all workshops in the system.
     * @return List of WorkshopDto
     */
    List<WorkshopDTO> getAllWorkshops();

    /**
     * Retrieves only active workshops (Scheduled or Ongoing) suitable for farmer viewing.
     * @return List of WorkshopDto
     */
    List<WorkshopDTO> getActiveWorkshopsForFarmers();
    // ... your existing methods ...

    /**
     * Creates and schedules a new workshop in the database.
     */
    WorkshopDTO scheduleWorkshop(WorkshopDTO workshopDto);

    /**
     * Fetches the schedule for a specific Extension Officer.
     */
    List<WorkshopDTO> getWorkshopsByOfficer(Long officerId);

    /**
     * Updates the status of a specific workshop (e.g., Scheduled -> Ongoing).
     */
    WorkshopDTO updateWorkshopStatus(Long workshopId, String status);

    // --- NEW: FULLY EDIT A WORKSHOP ---
    WorkshopDTO updateWorkshop(Long workshopId, WorkshopDTO dto);
    void deleteWorkshop(Long workshopId);
}
