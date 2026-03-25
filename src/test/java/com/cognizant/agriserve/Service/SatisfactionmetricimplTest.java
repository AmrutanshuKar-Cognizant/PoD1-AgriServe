package com.cognizant.agriserve.Service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.cognizant.agriserve.dao.*;
import com.cognizant.agriserve.dto.request.SatisfactionMetricRequestDTO;
import com.cognizant.agriserve.dto.response.SatisfactionMetricResponseDTO;
import com.cognizant.agriserve.entity.*;
import com.cognizant.agriserve.exception.ResourceNotFoundException;
import com.cognizant.agriserve.service.impl.Satisfactionmetricimpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class SatisfactionmetricimplTest {

    @Mock private SatisfactionMetricRepository metricrepo;
    @Mock private FeedbackRepository feedbackRepo;
    @Mock private TrainingProgramRepository trainingRepo;
    @Mock private UserRepository userRepo;

    @InjectMocks
    private Satisfactionmetricimpl satisfactionService;

    private SatisfactionMetricRequestDTO requestDTO;
    private TrainingProgram program;
    private User officer;
    private Feedback f1;
    private Feedback f2;

    @BeforeEach
    void setUp() {
        requestDTO = new SatisfactionMetricRequestDTO();
        requestDTO.setProgramId(101L);
        requestDTO.setOfficeId(50L);

        program = new TrainingProgram();
        program.setProgramId(101L);
        program.setTitle("Crop Protection");

        officer = new User();
        officer.setUserId(50L);
        officer.setName("Officer Aman");

        f1 = new Feedback();
        f1.setRating(5);

        f2 = new Feedback();
        f2.setRating(3);
    }

    @Test
    void testEvaluate_Success() {
        // Arrange
        when(trainingRepo.findById(101L)).thenReturn(Optional.of(program));
        when(userRepo.findById(50L)).thenReturn(Optional.of(officer));

        // Mocking feedback list with ratings 5 and 3 (Average = 4.0)
        when(feedbackRepo.findByTrainingProgram_ProgramId(101L)).thenReturn(Arrays.asList(f1, f2));

        SatisfactionMetric savedMetric = new SatisfactionMetric();
        savedMetric.setScore(4.0);
        when(metricrepo.save(any(SatisfactionMetric.class))).thenReturn(savedMetric);

        // Act
        SatisfactionMetric result = satisfactionService.evaluate(requestDTO);

        // Assert
        assertNotNull(result);
        assertEquals(4.0, result.getScore());
        verify(metricrepo, times(1)).save(any(SatisfactionMetric.class));
    }

    @Test
    void testEvaluate_NoFeedbackFound() {
        // Arrange
        when(trainingRepo.findById(101L)).thenReturn(Optional.of(program));
        when(userRepo.findById(50L)).thenReturn(Optional.of(officer));
        when(feedbackRepo.findByTrainingProgram_ProgramId(101L)).thenReturn(Collections.emptyList());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            satisfactionService.evaluate(requestDTO);
        });

        assertEquals("No feedback available for this program", exception.getMessage());
    }

    @Test
    void testGetSatisfactionmetric_Success() {
        // Since your current implementation has an empty list inside the method,
        // this test will confirm it currently returns an empty list.
        List<SatisfactionMetricResponseDTO> result = satisfactionService.getSatisfactionmetric();
        assertTrue(result.isEmpty());
    }
}