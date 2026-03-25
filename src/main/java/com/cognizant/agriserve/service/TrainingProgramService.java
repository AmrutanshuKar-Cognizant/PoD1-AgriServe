package com.cognizant.agriserve.service;

import com.cognizant.agriserve.dto.TrainingProgramDTO;
import java.util.List;


public interface TrainingProgramService {


    TrainingProgramDTO createProgram(TrainingProgramDTO dto);


    List<TrainingProgramDTO> getAllPrograms();

    TrainingProgramDTO getProgramById(Long programId);
    TrainingProgramDTO updateProgram(Long programId, TrainingProgramDTO dto);
    void deleteProgram(Long programId);
}