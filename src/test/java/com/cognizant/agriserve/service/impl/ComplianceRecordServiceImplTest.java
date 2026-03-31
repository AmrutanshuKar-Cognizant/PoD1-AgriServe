package com.cognizant.agriserve.service.impl;

import com.cognizant.agriserve.dao.AdvisorySessionRepository;
import com.cognizant.agriserve.dao.ComplianceRecordRepository;
import com.cognizant.agriserve.dao.TrainingProgramRepository;
import com.cognizant.agriserve.dao.UserRepository;
import com.cognizant.agriserve.dto.request.ComplianceRecordRequestDTO;
import com.cognizant.agriserve.dto.response.ComplianceRecordResponseDTO;
import com.cognizant.agriserve.entity.ComplianceRecord;
import com.cognizant.agriserve.entity.ComplianceRecord.ComplianceType;
import com.cognizant.agriserve.entity.User;
import com.cognizant.agriserve.exception.ResourceNotFoundException;
import com.cognizant.agriserve.exception.UnauthorizedActionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ComplianceRecordServiceImplTest {

    @Mock private ComplianceRecordRepository complianceRecordRepository;
    @Mock private TrainingProgramRepository trainingProgramRepository;
    @Mock private AdvisorySessionRepository advisorySessionRepository;
    @Mock private UserRepository userRepository;
    @Mock private ModelMapper modelMapper;
    @Mock private Authentication authentication;
    @Mock private SecurityContext securityContext;

    @InjectMocks
    private ComplianceRecordServiceImpl complianceRecordService;

    private User mockUser;
    private ComplianceRecordRequestDTO requestDTO;
    private ComplianceRecord complianceRecord;

    @BeforeEach
    void setUp() {
        // Setup Security Context Mocking
        SecurityContextHolder.setContext(securityContext);

        mockUser = new User();
        mockUser.setUserId(1L);
        mockUser.setEmail("officer@agriserve.com");

        requestDTO = new ComplianceRecordRequestDTO();
        requestDTO.setEntityId(100L);
        requestDTO.setType(ComplianceType.TRAINING);

        complianceRecord = new ComplianceRecord();
        complianceRecord.setComplianceId(1L);
        complianceRecord.setOfficerId(1L);
        complianceRecord.setEntityId(100L);
    }

    private void mockUserAuthentication() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getName()).thenReturn("officer@agriserve.com");
        when(userRepository.findByEmail("officer@agriserve.com")).thenReturn(Optional.of(mockUser));
    }

    @Test
    void createComplianceRecord_Success() {
        // Arrange
        mockUserAuthentication();
        when(trainingProgramRepository.existsById(100L)).thenReturn(true);
        when(modelMapper.map(any(ComplianceRecordRequestDTO.class), eq(ComplianceRecord.class))).thenReturn(complianceRecord);
        when(complianceRecordRepository.save(any(ComplianceRecord.class))).thenReturn(complianceRecord);
        when(modelMapper.map(any(ComplianceRecord.class), eq(ComplianceRecordResponseDTO.class))).thenReturn(new ComplianceRecordResponseDTO());

        // Act
        ComplianceRecordResponseDTO response = complianceRecordService.createComplianceRecord(requestDTO);

        // Assert
        assertNotNull(response);
        verify(complianceRecordRepository, times(1)).save(any());
    }

    @Test
    void createComplianceRecord_ThrowsException_WhenEntityNotFound() {
        // Arrange
        mockUserAuthentication();
        when(trainingProgramRepository.existsById(100L)).thenReturn(false);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            complianceRecordService.createComplianceRecord(requestDTO);
        });
    }

    @Test
    void getComplianceRecordById_Success() {
        // Arrange
        when(complianceRecordRepository.findById(1L)).thenReturn(Optional.of(complianceRecord));
        when(modelMapper.map(any(), any())).thenReturn(new ComplianceRecordResponseDTO());

        // Act
        ComplianceRecordResponseDTO response = complianceRecordService.getComplianceRecordById(1L);

        // Assert
        assertNotNull(response);
    }

    @Test
    void updateComplianceRecord_Unauthorized_ThrowsException() {
        // Arrange
        mockUserAuthentication(); // User is ID 1
        ComplianceRecord otherRecord = new ComplianceRecord();
        otherRecord.setOfficerId(2L); // Owned by User 2

        when(complianceRecordRepository.findById(1L)).thenReturn(Optional.of(otherRecord));

        // Act & Assert
        assertThrows(UnauthorizedActionException.class, () -> {
            complianceRecordService.updateComplianceRecord(1L, requestDTO);
        });
    }

    @Test
    void deleteComplianceRecord_Success() {
        // Arrange
        mockUserAuthentication();
        when(complianceRecordRepository.findById(1L)).thenReturn(Optional.of(complianceRecord));

        // Act
        complianceRecordService.deleteComplianceRecord(1L);

        // Assert
        verify(complianceRecordRepository, times(1)).delete(complianceRecord);
    }
}