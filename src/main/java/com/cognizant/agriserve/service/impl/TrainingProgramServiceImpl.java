package com.cognizant.agriserve.service.impl;

import com.cognizant.agriserve.dao.TrainingProgramRepository;
import com.cognizant.agriserve.dao.UserRepository;
import com.cognizant.agriserve.dto.TrainingProgramDTO;
import com.cognizant.agriserve.entity.TrainingProgram;
import com.cognizant.agriserve.entity.User;
import com.cognizant.agriserve.exception.ApiException;
import com.cognizant.agriserve.exception.ResourceNotFoundException;
import com.cognizant.agriserve.exception.UnauthorizedActionException;
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
        log.info("Validating business rules and fetching JWT identity...");

        String loggedInUserEmail = org.springframework.security.core.context.SecurityContextHolder
                .getContext().getAuthentication().getName();

        User manager = userRepository.findByEmail(loggedInUserEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Manager", "Email", loggedInUserEmail));

        if (manager.getRole() != User.Role.ProgramManager && manager.getRole() != User.Role.Admin) {
            log.error("Security breach attempt: User {} tried to create a program.", manager.getUserId());
            throw new UnauthorizedActionException(
                    "User ID " + manager.getUserId() + " does not have permission to create training programs."
            );
        }

        if (dto.getStartDate() != null && dto.getEndDate() != null && dto.getStartDate().isAfter(dto.getEndDate())) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Program start date cannot be later than the end date.");
        }

        TrainingProgram newProgram = modelMapper.map(dto, TrainingProgram.class);

        newProgram.setManager(manager);
        newProgram.setStatus(dto.getStatus() != null ? dto.getStatus() : "Draft");

        log.info("Saving Training Program to database...");
        TrainingProgram savedProgram = programRepository.save(newProgram);

        TrainingProgramDTO responseDto = modelMapper.map(savedProgram, TrainingProgramDTO.class);

        responseDto.setManagerId(manager.getUserId());

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
                        dto.setManagerId(program.getManager().getUserId().longValue());
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public TrainingProgramDTO getProgramById(Long programId) {
        log.info("Fetching Training Program details for ID: {}", programId);

        TrainingProgram program = programRepository.findById(programId)
                .orElseThrow(() -> new ResourceNotFoundException("Training Program", "ID", programId));

        TrainingProgramDTO dto = modelMapper.map(program, TrainingProgramDTO.class);
        if (program.getManager() != null) {
            dto.setManagerId(program.getManager().getUserId().longValue());
        }

        return dto;
    }

    @Override
    public TrainingProgramDTO updateProgram(Long programId, TrainingProgramDTO dto) {
        log.info("Attempting to update Training Program ID: {}", programId);

        TrainingProgram existingProgram = programRepository.findById(programId)
                .orElseThrow(() -> new ResourceNotFoundException("Training Program", "ID", programId));

        if (dto.getStartDate() != null && dto.getEndDate() != null && dto.getStartDate().isAfter(dto.getEndDate())) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Program start date cannot be later than the end date.");
        }

        existingProgram.setTitle(dto.getTitle());
        existingProgram.setDescription(dto.getDescription());
        existingProgram.setStartDate(dto.getStartDate());
        existingProgram.setEndDate(dto.getEndDate());
        if (dto.getStatus() != null) {
            existingProgram.setStatus(dto.getStatus());
        }

        TrainingProgram updatedProgram = programRepository.save(existingProgram);
        TrainingProgramDTO responseDto = modelMapper.map(updatedProgram, TrainingProgramDTO.class);
        if (updatedProgram.getManager() != null) responseDto.setManagerId(updatedProgram.getManager().getUserId().longValue());
        return responseDto;
    }

    @Override
    public void deleteProgram(Long programId) {
        log.info("Attempting to delete Training Program ID: {}", programId);
        TrainingProgram existingProgram = programRepository.findById(programId)
                .orElseThrow(() -> new ResourceNotFoundException("Training Program", "ID", programId));

        programRepository.delete(existingProgram);
        log.info("Successfully deleted Training Program ID: {}", programId);
    }
}