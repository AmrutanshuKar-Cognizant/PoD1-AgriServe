package com.cognizant.agriserve.controller;

import com.cognizant.agriserve.dto.FeedbackDTO;
import com.cognizant.agriserve.entity.Feedback;
import com.cognizant.agriserve.service.FeedbackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @PostMapping("/Submit")
    public ResponseEntity<Feedback> submitfeedback(@RequestBody FeedbackDTO dto) {
        log.info("REST request to submit feedback for Farmer ID: {}", dto.getFarmerId());
        return ResponseEntity.ok(feedbackService.addFeedback(dto));
    }

    @GetMapping("/all")
    public List<Feedback> getAll() {
        log.info("REST request to fetch all feedback records");
        return feedbackService.getAllFeedback();
    }
}