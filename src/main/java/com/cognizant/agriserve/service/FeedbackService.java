package com.cognizant.agriserve.service;

import com.cognizant.agriserve.dao.*;
import com.cognizant.agriserve.dto.FeedbackDTO;
import com.cognizant.agriserve.dto.FeedbackResponseDTO;
import com.cognizant.agriserve.entity.*;
import java.util.List;


public interface FeedbackService {
   Feedback addFeedback(FeedbackDTO dto);
   List<FeedbackResponseDTO> getAllFeedback();
}