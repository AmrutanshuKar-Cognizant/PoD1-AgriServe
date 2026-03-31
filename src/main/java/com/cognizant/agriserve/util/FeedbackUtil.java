package com.cognizant.agriserve.util;

import com.cognizant.agriserve.dto.request.FeedbackRequestDTO;
import com.cognizant.agriserve.entity.*;
import java.time.LocalDate;

public class FeedbackUtil {
    public static Feedback tofeedback(FeedbackRequestDTO dto, Farmer farmer, AdvisorySession session, TrainingProgram program) {
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