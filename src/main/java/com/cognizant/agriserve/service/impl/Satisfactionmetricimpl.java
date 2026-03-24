package com.cognizant.agriserve.service.impl;

import com.cognizant.agriserve.dao.FeedbackRepository;
import com.cognizant.agriserve.dao.SatisfactionMetricRepository;
import com.cognizant.agriserve.dao.TrainingProgramRepository;
import com.cognizant.agriserve.dao.UserRepository;
import com.cognizant.agriserve.dto.SatisfactionMetricDTO;
import com.cognizant.agriserve.dto.SatisfactionMetricResponseDTO;
import com.cognizant.agriserve.entity.Feedback;
import com.cognizant.agriserve.entity.SatisfactionMetric;
import com.cognizant.agriserve.entity.TrainingProgram;
import com.cognizant.agriserve.entity.User;
import com.cognizant.agriserve.exception.ResourceNotFoundException;
import com.cognizant.agriserve.service.SatisfactionMetricservice;
import com.cognizant.agriserve.util.Satisfactionutil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class Satisfactionmetricimpl implements SatisfactionMetricservice {
    @Autowired private SatisfactionMetricRepository metricrepo;
    @Autowired private FeedbackRepository feedbackRepo;
    @Autowired private TrainingProgramRepository trainingRepo;
    @Autowired private UserRepository userRepo;
    @Override
    public SatisfactionMetric evaluate(SatisfactionMetricDTO dto) {
        log.info("Calculating performance metrics for Program ID: {}", dto.getProgramId());

        // 1. Fetch dependencies
        TrainingProgram p = trainingRepo.findById(dto.getProgramId())
                .orElseThrow(() -> new ResourceNotFoundException("Program not found"));

        User m = userRepo.findById(dto.getOfficeId())
                .orElseThrow(() -> new ResourceNotFoundException("Officer not found"));

        // 2. Aggregate logic: Fetch all feedback for this program
        List<Feedback> list = feedbackRepo.findByTrainingProgram_ProgramId(dto.getProgramId());

        if (list.isEmpty()) {
            log.warn("No feedback found for Program ID: {}", dto.getProgramId());
            throw new RuntimeException("No feedback available for this program");
        }

        // 3. Calculate average score
        double average = list.stream()
                .mapToDouble(Feedback::getRating)
                .average()
                .orElse(0.0);

        log.info("Calculated Average Score for Program {}: {}", dto.getProgramId(), average);

        // 4. Use Utility to map and Save
        SatisfactionMetric metric = Satisfactionutil.Satisfactionutili(dto, p, m, average);

        return metricrepo.save(metric);
    }

    public List<SatisfactionMetricResponseDTO> getSatisfactionmetric(){
        List<SatisfactionMetric>entites =new ArrayList<>();

        return entites.stream().map(entity->{
            SatisfactionMetricResponseDTO dto=new SatisfactionMetricResponseDTO();
            dto.setProgramId(entity.getTrainingProgram().getProgramId());
            dto.setStatus(entity.getStatus());
            dto.setScore(entity.getScore());
            return dto;
        }).toList();
    }
}
