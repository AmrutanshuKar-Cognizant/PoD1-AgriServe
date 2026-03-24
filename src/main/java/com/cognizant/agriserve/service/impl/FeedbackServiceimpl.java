package com.cognizant.agriserve.service.impl;

import com.cognizant.agriserve.dao.AdvisorySessionRepository;
import com.cognizant.agriserve.dao.FarmerRepository;
import com.cognizant.agriserve.dao.FeedbackRepository;
import com.cognizant.agriserve.dao.TrainingProgramRepository;
import com.cognizant.agriserve.dto.FeedbackDTO;
import com.cognizant.agriserve.dto.FeedbackResponseDTO;
import com.cognizant.agriserve.entity.AdvisorySession;
import com.cognizant.agriserve.entity.Farmer;
import com.cognizant.agriserve.entity.Feedback;
import com.cognizant.agriserve.entity.TrainingProgram;
import com.cognizant.agriserve.exception.ResourceNotFoundException;
import com.cognizant.agriserve.service.FeedbackService;
import com.cognizant.agriserve.util.feedbackutil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Slf4j
public class FeedbackServiceimpl implements FeedbackService {
    @Autowired
    private FeedbackRepository feedbackRepo;
    @Autowired private FarmerRepository farmerRepo;
    @Autowired private AdvisorySessionRepository sessionRepo;
    @Autowired private TrainingProgramRepository trainingRepo;
    @Override
    public Feedback addFeedback(FeedbackDTO dto) {
        log.info("Recording farmer feedback for Session: {}", dto.getSessionId());
        Farmer f = farmerRepo.findById(dto.getFarmerId()).orElseThrow(() -> new ResourceNotFoundException("Farmer not found"));
        AdvisorySession s = sessionRepo.findById(dto.getSessionId()).orElseThrow(() -> new ResourceNotFoundException("Session not found"));
        TrainingProgram p = trainingRepo.findById(dto.getProgramId()).orElseThrow(() -> new ResourceNotFoundException("Program not found"));

        return feedbackRepo.save(feedbackutil.tofeedback(dto, f, s, p));
    }
    @Override
    public List<FeedbackResponseDTO> getAllFeedback() {
        List<Feedback> entities=feedbackRepo.findAll();

        return entities.stream().map(entity-> {
            FeedbackResponseDTO dto = new FeedbackResponseDTO();
            dto.setFeedbackId(entity.getFeedbackId());
            dto.setFarmerName(entity.getFarmer().getName());
            dto.setProgramName(entity.getTrainingProgram().getTitle());
            dto.setRating(entity.getRating());
            dto.setComments(entity.getComments());
            return dto;
        }).toList();
    }
}
