package com.cognizant.agriserve.service.impl;

import com.cognizant.agriserve.dao.WorkshopRepository;
import com.cognizant.agriserve.dto.WorkshopDto;
import com.cognizant.agriserve.entity.Workshop;
import com.cognizant.agriserve.service.WorkshopService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkshopServiceImpl implements WorkshopService {

    private final WorkshopRepository workshopRepository;

    // Constructor injection for required dependencies
    public WorkshopServiceImpl(WorkshopRepository workshopRepository) {
        this.workshopRepository = workshopRepository;
    }

    @Override
    public List<WorkshopDto> getAllWorkshops() {
        List<Workshop> rawWorkshops = workshopRepository.findAll();

        // Map entities to DTOs to ensure database models are not exposed to the presentation layer
        return rawWorkshops.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<WorkshopDto> getActiveWorkshopsForFarmers() {
        List<Workshop> allWorkshops = workshopRepository.findAll();

        // Filter out canceled or completed workshops so farmers only see relevant upcoming sessions
        return allWorkshops.stream()
                .filter(w -> "Scheduled".equals(w.getStatus()) || "Ongoing".equals(w.getStatus()))
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    // ... your existing methods ...

    @Override
    public WorkshopDto scheduleWorkshop(WorkshopDto dto) {
        // Map the incoming DTO to a raw Entity
        Workshop newWorkshop = new Workshop();
        newWorkshop.setOfficerId(dto.getOfficerId());
        newWorkshop.setLocation(dto.getLocation());
        newWorkshop.setDate(dto.getDate());

        // Apply Business Rule: A brand new workshop should always start as 'Scheduled'
        newWorkshop.setStatus("Scheduled");

        // Save to the database
        Workshop savedWorkshop = workshopRepository.save(newWorkshop);

        // Convert the saved entity back to a DTO to send to the frontend
        return convertToDto(savedWorkshop);
    }

    @Override
    public List<WorkshopDto> getWorkshopsByOfficer(Long officerId) {
        // Uses the custom method we added to your JpaRepository earlier
        List<Workshop> officerWorkshops = workshopRepository.findByOfficerId(officerId);

        return officerWorkshops.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public WorkshopDto updateWorkshopStatus(Long workshopId, String status) {
        // 1. Find the workshop. If it doesn't exist, throw an error.
        Workshop existingWorkshop = workshopRepository.findById(workshopId)
                .orElseThrow(() -> new RuntimeException("Workshop not found with ID: " + workshopId));

        // 2. Update the status
        existingWorkshop.setStatus(status);

        // 3. Save and return
        Workshop updatedWorkshop = workshopRepository.save(existingWorkshop);
        return convertToDto(updatedWorkshop);
    }

    /**
     * Helper method to map a Workshop entity to a WorkshopDto.
     */
    private WorkshopDto convertToDto(Workshop workshop) {
        return new WorkshopDto(
                workshop.getWorkshopId(),
                workshop.getTrainingProgram() != null ? workshop.getTrainingProgram().getTitle() : null,
                workshop.getOfficerId(),
                workshop.getLocation(),
                workshop.getDate(),
                workshop.getStatus()
        );
    }
}