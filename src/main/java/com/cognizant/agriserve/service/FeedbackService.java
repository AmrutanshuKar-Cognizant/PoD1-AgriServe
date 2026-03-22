package com.cognizant.agriserve.service;

import com.cognizant.agriserve.dao.*;
import com.cognizant.agriserve.dto.FeedbackDTO;
import com.cognizant.agriserve.entity.*;
import com.cognizant.agriserve.exception.ResourceNotFoundException;
import com.cognizant.agriserve.util.feedbackutil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
public class FeedbackService {
    @Autowired private FeedbackRepository feedbackRepo;
    @Autowired private FarmerRepository farmerRepo;
    @Autowired private AdvisorySessionRepository sessionRepo;
    @Autowired private TrainingProgramRepository trainingRepo;

    public Feedback addFeedback(FeedbackDTO dto) {
        log.info("Recording farmer feedback for Session: {}", dto.getSessionId());
        Farmer f = farmerRepo.findById(dto.getFarmerId()).orElseThrow(() -> new ResourceNotFoundException("Farmer not found"));
        AdvisorySession s = sessionRepo.findById(dto.getSessionId()).orElseThrow(() -> new ResourceNotFoundException("Session not found"));
        TrainingProgram p = trainingRepo.findById(dto.getProgramId()).orElseThrow(() -> new ResourceNotFoundException("Program not found"));

        return feedbackRepo.save(feedbackutil.tofeedback(dto, f, s, p));
    }

    public List<Feedback> getAllFeedback() { return feedbackRepo.findAll(); }
}