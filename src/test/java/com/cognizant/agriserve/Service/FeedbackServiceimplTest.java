package com.cognizant.agriserve.Service; // Use the implementation package

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.cognizant.agriserve.dao.*;
import com.cognizant.agriserve.dto.request.FeedbackRequestDTO;
import com.cognizant.agriserve.dto.response.FeedbackResponseDTO;
import com.cognizant.agriserve.entity.*;
import com.cognizant.agriserve.exception.ResourceNotFoundException;
import com.cognizant.agriserve.service.impl.FeedbackServiceimpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class FeedbackServiceimplTest {

    @Mock private FeedbackRepository feedbackRepo;
    @Mock private FarmerRepository farmerRepo;
    @Mock private AdvisorySessionRepository sessionRepo;
    @Mock private TrainingProgramRepository trainingRepo;

    // FIX: Inject into the actual Implementation class, not the Test class!
    @InjectMocks
    private FeedbackServiceimpl feedbackService;

    private FeedbackRequestDTO requestDTO;
    private Farmer farmer;
    private AdvisorySession session;
    private TrainingProgram program;
    private Feedback feedback;

    @BeforeEach
    void setUp() {
        requestDTO = new FeedbackRequestDTO();
        requestDTO.setFarmerId(1L);
        requestDTO.setSessionId(10L);
        requestDTO.setProgramId(100L);
        requestDTO.setRating(5);
        requestDTO.setComment("Excellent training!");

        farmer = new Farmer();
        farmer.setFarmerId(1L);
        farmer.setName("Sumit");

        session = new AdvisorySession();
        session.setSessionId(10L);

        program = new TrainingProgram();
        program.setProgramId(100L);
        program.setTitle("Organic Farming 101");

        feedback = new Feedback();
        feedback.setFeedbackId(500L);
        feedback.setFarmer(farmer);
        feedback.setTrainingProgram(program);
        feedback.setRating(5);
        feedback.setComments("Excellent training!");
    }

    @Test
    void testAddFeedback_Success() {
        // Arrange
        when(farmerRepo.findById(1L)).thenReturn(Optional.of(farmer));
        when(sessionRepo.findById(10L)).thenReturn(Optional.of(session));
        when(trainingRepo.findById(100L)).thenReturn(Optional.of(program));
        when(feedbackRepo.save(any(Feedback.class))).thenReturn(feedback);

        // Act: Now this calls the real service logic
        Feedback result = feedbackService.addFeedback(requestDTO);

        // Assert
        assertNotNull(result);
        assertEquals(5, result.getRating());
        verify(feedbackRepo, times(1)).save(any(Feedback.class));
    }

    @Test
    void testAddFeedback_FarmerNotFound() {
        when(farmerRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            feedbackService.addFeedback(requestDTO);
        });
    }

    @Test
    void testGetAllFeedback_Success() {
        when(feedbackRepo.findAll()).thenReturn(Arrays.asList(feedback));

        List<FeedbackResponseDTO> result = feedbackService.getAllFeedback();

        assertFalse(result.isEmpty());
        assertEquals("Sumit", result.get(0).getFarmerName());
        assertEquals("Organic Farming 101", result.get(0).getProgramName());
    }
}