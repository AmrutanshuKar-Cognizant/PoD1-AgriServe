package com.cognizant.agriserve.service.impl;

import com.cognizant.agriserve.dao.WorkshopRepository;
import com.cognizant.agriserve.dto.WorkshopDTO;
import com.cognizant.agriserve.entity.Workshop;
import com.cognizant.agriserve.service.WorkshopService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class WorkshopServiceImpl implements WorkshopService {

    private final WorkshopRepository workshopRepository;
    private final ModelMapper modelMapper;

    public WorkshopServiceImpl(WorkshopRepository workshopRepository, ModelMapper modelMapper) {
        this.workshopRepository = workshopRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<WorkshopDTO> getAllWorkshops() {
        log.info("Fetching all workshops");
        return workshopRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<WorkshopDTO> getActiveWorkshopsForFarmers() {
        log.info("Filtering active workshops for farmers");
        return workshopRepository.findAll().stream()
                .filter(w -> "Scheduled".equals(w.getStatus()) || "Ongoing".equals(w.getStatus()))
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public WorkshopDTO scheduleWorkshop(WorkshopDTO dto) {
        log.info("Scheduling a new workshop");
        Workshop newWorkshop = modelMapper.map(dto, Workshop.class);
        newWorkshop.setStatus("Scheduled");
        Workshop savedWorkshop = workshopRepository.save(newWorkshop);
        return convertToDto(savedWorkshop);
    }

    @Override
    public List<WorkshopDTO> getWorkshopsByOfficer(Long officerId) {
        log.info("Fetching workshops for Officer ID: {}", officerId);
        return workshopRepository.findByOfficerId(officerId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public WorkshopDTO updateWorkshopStatus(Long workshopId, String status) {
        log.info("Updating status for workshop ID: {}", workshopId);

        // Fetch Workshop (404 Not Found)
        Workshop existingWorkshop = workshopRepository.findById(workshopId)
                .orElseThrow(() -> new ResourceNotFoundException("Workshop", "ID", workshopId));

        existingWorkshop.setStatus(status);
        Workshop updatedWorkshop = workshopRepository.save(existingWorkshop);
        return convertToDto(updatedWorkshop);
    }
    // --- NEW: FULLY EDIT A WORKSHOP ---
    @Override
    public WorkshopDTO updateWorkshop(Long workshopId, WorkshopDTO dto) {
        log.info("Attempting to edit details for Workshop ID: {}", workshopId);

        Workshop existingWorkshop = workshopRepository.findById(workshopId)
                .orElseThrow(() -> new ResourceNotFoundException("Workshop", "ID", workshopId));

        // Update core details
        existingWorkshop.setLocation(dto.getLocation());
        existingWorkshop.setDate(dto.getDate());
        existingWorkshop.setOfficerId(dto.getOfficerId());

        Workshop updatedWorkshop = workshopRepository.save(existingWorkshop);
        return convertToDto(updatedWorkshop);
    }

    // --- NEW: DELETE A WORKSHOP ---
    @Override
    public void deleteWorkshop(Long workshopId) {
        log.info("Attempting to delete Workshop ID: {}", workshopId);
        Workshop existingWorkshop = workshopRepository.findById(workshopId)
                .orElseThrow(() -> new ResourceNotFoundException("Workshop", "ID", workshopId));

        workshopRepository.delete(existingWorkshop);
        log.info("Successfully deleted Workshop ID: {}", workshopId);
    }

    private WorkshopDTO convertToDto(Workshop workshop) {
        WorkshopDTO dto = modelMapper.map(workshop, WorkshopDTO.class);
        if (workshop.getTrainingProgram() != null) {
            dto.setProgramTitle(workshop.getTrainingProgram().getTitle());
        }
        return dto;
    }
}