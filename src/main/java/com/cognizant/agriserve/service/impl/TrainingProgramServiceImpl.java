package com.cognizant.agriserve.service.impl;

import com.cognizant.agriserve.dao.TrainingProgramRepository;
import com.cognizant.agriserve.dto.TrainingProgramDto;
import com.cognizant.agriserve.entity.TrainingProgram;
import com.cognizant.agriserve.service.TrainingProgramService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrainingProgramServiceImpl implements TrainingProgramService {

    private final TrainingProgramRepository programRepository;

    public TrainingProgramServiceImpl(TrainingProgramRepository programRepository) {
        this.programRepository = programRepository;
    }

    @Override
    public TrainingProgramDto createProgram(TrainingProgramDto dto) {

        if (dto.getStartDate() != null && dto.getEndDate() != null
                && dto.getStartDate().isAfter(dto.getEndDate())) {
            throw new IllegalArgumentException("Program start date cannot be later than the end date.");
        }

        TrainingProgram newProgram = new TrainingProgram();
        newProgram.setTitle(dto.getTitle());
        newProgram.setDescription(dto.getDescription());
        newProgram.setStartDate(dto.getStartDate());
        newProgram.setEndDate(dto.getEndDate());

        // NEW LINE: Set the manager ID from the incoming DTO
        newProgram.setManagerId(dto.getManagerId());

        newProgram.setStatus(dto.getStatus() != null ? dto.getStatus() : "Draft");

        TrainingProgram savedProgram = programRepository.save(newProgram);

        // Map the generated ID and final status back to the DTO
        dto.setProgramId(savedProgram.getProgramId());
        dto.setStatus(savedProgram.getStatus());

        return dto;
    }

    @Override
    public List<TrainingProgramDto> getAllPrograms() {
        return programRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Helper method to map Entity to DTO
     */
    private TrainingProgramDto convertToDto(TrainingProgram program) {
        // Updated to include the Manager ID in the response
        return new TrainingProgramDto(
                program.getProgramId(),
                program.getTitle(),
                program.getDescription(),
                program.getStartDate(),
                program.getEndDate(),
                program.getStatus(),
                program.getManagerId() // NEW LINE
        );
    }
}