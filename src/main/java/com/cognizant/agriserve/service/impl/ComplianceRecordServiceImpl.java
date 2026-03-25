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
import com.cognizant.agriserve.service.ComplianceRecordService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ComplianceRecordServiceImpl implements ComplianceRecordService {

    private final ComplianceRecordRepository complianceRecordRepository;
    private final TrainingProgramRepository trainingProgramRepository;
    private final AdvisorySessionRepository advisorySessionRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public ComplianceRecordServiceImpl(ComplianceRecordRepository complianceRecordRepository,
                                       TrainingProgramRepository trainingProgramRepository,
                                       AdvisorySessionRepository advisorySessionRepository,
                                       UserRepository userRepository,
                                       ModelMapper modelMapper) {
        this.complianceRecordRepository = complianceRecordRepository;
        this.trainingProgramRepository = trainingProgramRepository;
        this.advisorySessionRepository = advisorySessionRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    private Long getCurrentLoggedInUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            throw new UnauthorizedActionException("User is not authenticated. Please provide a valid JWT.");
        }

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Logged in user not found in database."));

        return user.getUserId();
    }

    @Override
    public ComplianceRecordResponseDTO createComplianceRecord(ComplianceRecordRequestDTO requestDTO) {

        Long currentLoggedInUserId = getCurrentLoggedInUserId();

        log.info("Officer ID {} is attempting to create a new {} record for Entity ID {}",
                currentLoggedInUserId, requestDTO.getType(), requestDTO.getEntityId());

        Long targetId = requestDTO.getEntityId();
        if (requestDTO.getType() == ComplianceType.TRAINING) {
            if (!trainingProgramRepository.existsById(targetId)) {
                log.warn("Failed creation: Officer {} tried to use invalid Training Program ID {}", currentLoggedInUserId, targetId);
                throw new ResourceNotFoundException("Cannot create record. No Training Program found with ID: " + targetId);
            }
        } else if (requestDTO.getType() == ComplianceType.ADVISORY) {
            if (!advisorySessionRepository.existsById(targetId)) {
                log.warn("Failed creation: Officer {} tried to use invalid Advisory Session ID {}", currentLoggedInUserId, targetId);
                throw new ResourceNotFoundException("Cannot create record. No Advisory Session found with ID: " + targetId);
            }
        }

        ComplianceRecord complianceRecord = mapToEntity(requestDTO);
        complianceRecord.setOfficerId(currentLoggedInUserId);

        ComplianceRecord savedRecord = complianceRecordRepository.save(complianceRecord);
        log.info("Successfully created Compliance Record ID {} for Officer ID {}", savedRecord.getComplianceId(), currentLoggedInUserId);

        return mapToResponseDTO(savedRecord);
    }

    @Override
    public ComplianceRecordResponseDTO getComplianceRecordById(Long complianceId) {
        log.debug("Fetching Compliance Record with ID: {}", complianceId);

        ComplianceRecord record = complianceRecordRepository.findById(complianceId)
                .orElseThrow(() -> new ResourceNotFoundException("Compliance Record not found with ID: " + complianceId));
        return mapToResponseDTO(record);
    }

    @Override
    public List<ComplianceRecordResponseDTO> getAllComplianceRecords() {
        log.debug("Fetching all Compliance Records");

        return complianceRecordRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ComplianceRecordResponseDTO> getRecordsByEntity(Long entityId) {
        log.debug("Fetching Compliance Records for Entity ID: {}", entityId);

        return complianceRecordRepository.findByEntityId(entityId).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ComplianceRecordResponseDTO> getRecordsByType(ComplianceType type) {
        log.debug("Fetching Compliance Records of Type: {}", type);

        return complianceRecordRepository.findByType(type).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ComplianceRecordResponseDTO> getRecordsByEntityAndType(Long entityId, ComplianceType type) {
        log.debug("Fetching Compliance Records for Entity ID: {} and Type: {}", entityId, type);

        return complianceRecordRepository.findByEntityIdAndType(entityId, type).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ComplianceRecordResponseDTO updateComplianceRecord(Long complianceId, ComplianceRecordRequestDTO requestDTO) {

        Long currentLoggedInUserId = getCurrentLoggedInUserId();

        log.info("Officer ID {} is attempting to update Compliance Record ID {}", currentLoggedInUserId, complianceId);

        ComplianceRecord existingRecord = complianceRecordRepository.findById(complianceId)
                .orElseThrow(() -> new ResourceNotFoundException("Compliance Record not found with ID: " + complianceId));

        if (!existingRecord.getOfficerId().equals(currentLoggedInUserId)) {
            log.warn("SECURITY BLOCKED: Officer ID {} attempted to update Record ID {} which they do not own.", currentLoggedInUserId, complianceId);
            throw new UnauthorizedActionException("Access Denied: You can only edit your own compliance records.");
        }

        Long targetId = requestDTO.getEntityId();
        if (requestDTO.getType() == ComplianceType.TRAINING) {
            if (!trainingProgramRepository.existsById(targetId)) {
                log.warn("Failed update: Officer {} tried to use invalid Training Program ID {}", currentLoggedInUserId, targetId);
                throw new ResourceNotFoundException("Cannot update record. No Training Program found with ID: " + targetId);
            }
        } else if (requestDTO.getType() == ComplianceType.ADVISORY) {
            if (!advisorySessionRepository.existsById(targetId)) {
                log.warn("Failed update: Officer {} tried to use invalid Advisory Session ID {}", currentLoggedInUserId, targetId);
                throw new ResourceNotFoundException("Cannot update record. No Advisory Session found with ID: " + targetId);
            }
        }

        existingRecord.setEntityId(requestDTO.getEntityId());
        existingRecord.setType(requestDTO.getType());
        existingRecord.setResult(requestDTO.getResult());
        existingRecord.setNotes(requestDTO.getNotes());

        ComplianceRecord updatedRecord = complianceRecordRepository.save(existingRecord);
        log.info("Successfully updated Compliance Record ID {}", updatedRecord.getComplianceId());

        return mapToResponseDTO(updatedRecord);
    }

    @Override
    public void deleteComplianceRecord(Long complianceId) {

        Long currentLoggedInUserId = getCurrentLoggedInUserId();

        log.info("Officer ID {} is attempting to DELETE Compliance Record ID {}", currentLoggedInUserId, complianceId);

        ComplianceRecord existingRecord = complianceRecordRepository.findById(complianceId)
                .orElseThrow(() -> new ResourceNotFoundException("Compliance Record not found with ID: " + complianceId));

        if (!existingRecord.getOfficerId().equals(currentLoggedInUserId)) {
            log.warn("SECURITY BLOCKED: Officer ID {} attempted to delete Record ID {} which they do not own.", currentLoggedInUserId, complianceId);
            throw new UnauthorizedActionException("Access Denied: You can only delete your own compliance records.");
        }

        complianceRecordRepository.delete(existingRecord);
        log.info("Successfully deleted Compliance Record ID {}", complianceId);
    }

    private ComplianceRecord mapToEntity(ComplianceRecordRequestDTO dto) {
        return modelMapper.map(dto, ComplianceRecord.class);
    }

    private ComplianceRecordResponseDTO mapToResponseDTO(ComplianceRecord entity) {
        return modelMapper.map(entity, ComplianceRecordResponseDTO.class);
    }
}