package com.cognizant.agriserve.service.impl;

import com.cognizant.agriserve.dao.FeedbackRepository;
import com.cognizant.agriserve.dto.FeedbackDTO;
import com.cognizant.agriserve.entity.Feedback;
import com.cognizant.agriserve.service.FeedbackService;
import com.cognizant.agriserve.util.feedbackutil;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class FeedbackServiceimpl implements FeedbackService {
    @Autowired private FeedbackRepository feedbackRepo;
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
    public List<Feedback> getAllFeedback() { return feedbackRepo.findAll(); }
}
