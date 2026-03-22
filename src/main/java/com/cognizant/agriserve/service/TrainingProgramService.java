package com.cognizant.agriserve.service;

import com.cognizant.agriserve.dto.TrainingProgramDto;
import java.util.List;

/**
 * Service interface defining the business operations for Training Programs.
 * Separating this interface from its implementation allows for loose coupling
 * and simplifies unit testing via mocking.
 */
public interface TrainingProgramService {

    /**
     * Persists a new training program after validating business rules.
     *
     * @param dto The data transfer object containing program details.
     * @return The saved program as a DTO, including its generated ID.
     * @throws IllegalArgumentException if the start date is after the end date.
     */
    TrainingProgramDto createProgram(TrainingProgramDto dto);

    /**
     * Retrieves all training programs in the system.
     *
     * @return A list of TrainingProgramDto objects.
     */
    List<TrainingProgramDto> getAllPrograms();
}