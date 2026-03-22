package com.cognizant.agriserve.util;

import com.cognizant.agriserve.dto.FeedbackDTO;
import com.cognizant.agriserve.entity.*;
import java.time.LocalDate;

public class feedbackutil {
    public static Feedback tofeedback(FeedbackDTO dto, Farmer farmer, AdvisorySession session, TrainingProgram program) {
        Feedback ft = new Feedback();
        ft.setFarmer(farmer);
        ft.setSession(session);
        ft.setTrainingProgram(program);
        ft.setRating(dto.getRating());
        ft.setComments(dto.getComment());
        ft.setDate(LocalDate.now());
        return ft;
    }
}