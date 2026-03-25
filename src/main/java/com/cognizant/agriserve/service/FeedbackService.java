package com.cognizant.agriserve.service;

import com.cognizant.agriserve.dto.request.FeedbackRequestDTO;
import com.cognizant.agriserve.dto.response.FeedbackResponseDTO;
import com.cognizant.agriserve.entity.*;
import java.util.List;


public interface FeedbackService {
   Feedback addFeedback(FeedbackRequestDTO dto);
   List<FeedbackResponseDTO> getAllFeedback();
}