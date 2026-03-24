package com.cognizant.agriserve.service.impl;

import com.cognizant.agriserve.dao.TrainingProgramRepository;
import com.cognizant.agriserve.dao.UserRepository;
import com.cognizant.agriserve.dto.TrainingProgramDTO;
import com.cognizant.agriserve.entity.TrainingProgram;
import com.cognizant.agriserve.entity.User;
import com.cognizant.agriserve.exception.ApiException;
import com.cognizant.agriserve.service.TrainingProgramService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TrainingProgramServiceImpl implements TrainingProgramService {

    private final TrainingProgramRepository programRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    // Constructor Injection
    public TrainingProgramServiceImpl(TrainingProgramRepository programRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.programRepository = programRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public TrainingProgramDTO createProgram(TrainingProgramDTO dto) {
        log.info("Validating business rules for new Training Program...");

        // 1. Business Rule Validation (400 Bad Request)
        if (dto.getStartDate() != null && dto.getEndDate() != null && dto.getStartDate().isAfter(dto.getEndDate())) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Program start date cannot be later than the end date.");
        }

        // 2. Fetch the Manager from the database (404 Not Found)
        log.info("Fetching Manager details for User ID: {}", dto.getManagerId());
        User manager = userRepository.findById(dto.getManagerId())
                .orElseThrow(() -> new ResourceNotFoundException("Manager", "ID", dto.getManagerId()));

        // 3. Security/Role Check (403 Forbidden)
        if (manager.getRole() != User.Role.ProgramManager && manager.getRole() != User.Role.Admin) {
            log.error("Security breach attempt: User {} tried to create a program without proper roles.", manager.getUserID());
            throw new UnauthorizedAccessException(
                    "User ID " + manager.getUserID() + " does not have permission to create training programs."
            );
        }

        // 4. Map DTO to Entity and set relationships
        TrainingProgram newProgram = modelMapper.map(dto, TrainingProgram.class);
        newProgram.setManager(manager);
        newProgram.setStatus(dto.getStatus() != null ? dto.getStatus() : "Draft");

        // 5. Save to Database
        log.info("Saving Training Program to database...");
        TrainingProgram savedProgram = programRepository.save(newProgram);

        // 6. Map back to DTO to return to the frontend
        TrainingProgramDTO responseDto = modelMapper.map(savedProgram, TrainingProgramDTO.class);
        responseDto.setManagerId(manager.getUserID());

        return responseDto;
    }

    @Override
    public List<TrainingProgramDTO> getAllPrograms() {
        log.info("Retrieving all Training Programs from DB");

        return programRepository.findAll()
                .stream()
                .map(program -> {
                    TrainingProgramDTO dto = modelMapper.map(program, TrainingProgramDTO.class);
                    if (program.getManager() != null) {
                        dto.setManagerId(program.getManager().getUserID());
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public TrainingProgramDTO getProgramById(Long programId) {
        log.info("Fetching Training Program details for ID: {}", programId);

        // Fetch Program (404 Not Found)
        TrainingProgram program = programRepository.findById(programId)
                .orElseThrow(() -> new ResourceNotFoundException("Training Program", "ID", programId));

        TrainingProgramDTO dto = modelMapper.map(program, TrainingProgramDTO.class);
        if (program.getManager() != null) {
            dto.setManagerId(program.getManager().getUserID());
        }

        return dto;
    }

    // --- NEW: EDIT A PROGRAM ---
    @Override
    public TrainingProgramDTO updateProgram(Long programId, TrainingProgramDTO dto) {
        log.info("Attempting to update Training Program ID: {}", programId);

        // 1. Check if program exists
        TrainingProgram existingProgram = programRepository.findById(programId)
                .orElseThrow(() -> new ResourceNotFoundException("Training Program", "ID", programId));

        // 2. Validate new dates
        if (dto.getStartDate() != null && dto.getEndDate() != null && dto.getStartDate().isAfter(dto.getEndDate())) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Program start date cannot be later than the end date.");
        }

        // 3. Update the fields
        existingProgram.setTitle(dto.getTitle());
        existingProgram.setDescription(dto.getDescription());
        existingProgram.setStartDate(dto.getStartDate());
        existingProgram.setEndDate(dto.getEndDate());
        if (dto.getStatus() != null) {
            existingProgram.setStatus(dto.getStatus());
        }

        // 4. Save and return
        TrainingProgram updatedProgram = programRepository.save(existingProgram);
        TrainingProgramDTO responseDto = modelMapper.map(updatedProgram, TrainingProgramDTO.class);
        if (updatedProgram.getManager() != null) responseDto.setManagerId(updatedProgram.getManager().getUserID());
        return responseDto;
    }

    // --- NEW: DELETE A PROGRAM ---
    @Override
    public void deleteProgram(Long programId) {
        log.info("Attempting to delete Training Program ID: {}", programId);
        TrainingProgram existingProgram = programRepository.findById(programId)
                .orElseThrow(() -> new ResourceNotFoundException("Training Program", "ID", programId));

        programRepository.delete(existingProgram);
        log.info("Successfully deleted Training Program ID: {}", programId);
    }
}