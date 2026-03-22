package com.cognizant.agriserve.service.impl;

import com.cognizant.agriserve.dto.AuditRequestDTO;
import com.cognizant.agriserve.dto.AuditResponseDTO;
import com.cognizant.agriserve.entity.Audit;
import com.cognizant.agriserve.entity.Audit.AuditStatus;
import com.cognizant.agriserve.dao.AuditRepository;
import com.cognizant.agriserve.dao.UserRepository;
import com.cognizant.agriserve.service.AuditService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import com.cognizant.agriserve.exception.ResourceNotFoundException;
import com.cognizant.agriserve.exception.UnauthorizedActionException;

@Slf4j
@Service
public class AuditServiceImpl implements AuditService {

    private final AuditRepository auditRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public AuditServiceImpl(AuditRepository auditRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.auditRepository = auditRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public AuditResponseDTO initiateAudit(AuditRequestDTO requestDTO, Long currentLoggedInUserId) {
        log.info("Officer ID {} is initiating a new Audit with scope: {}", currentLoggedInUserId, requestDTO.getScope());

        Audit audit = mapToEntity(requestDTO);
        audit.setOfficerId(currentLoggedInUserId);

        Audit savedAudit = auditRepository.save(audit);
        log.info("Successfully created Audit ID {} for Officer ID {}", savedAudit.getAuditId(), currentLoggedInUserId);

        return mapToResponseDTO(savedAudit);
    }

    @Override
    public AuditResponseDTO getAuditById(Long auditId) {
        log.debug("Fetching Audit with ID: {}", auditId);

        Audit audit = auditRepository.findById(auditId)
                .orElseThrow(() -> new ResourceNotFoundException("Audit not found with ID: " + auditId));
        return mapToResponseDTO(audit);
    }

    @Override
    public List<AuditResponseDTO> getAllAudits() {
        log.debug("Fetching all Audits");

        return auditRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AuditResponseDTO> getAuditsByOfficerId(Long officerId) {
        log.debug("Fetching Audits for Officer ID: {}", officerId);

        return auditRepository.findByOfficerId(officerId).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AuditResponseDTO> getAuditsByStatus(AuditStatus status) {
        log.debug("Fetching Audits with Status: {}", status);

        return auditRepository.findByStatus(status).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AuditResponseDTO> getAuditsByOfficerIdAndStatus(Long officerId, AuditStatus status) {
        log.debug("Fetching Audits for Officer ID: {} and Status: {}", officerId, status);

        return auditRepository.findByOfficerIdAndStatus(officerId, status).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AuditResponseDTO updateAudit(Long auditId, AuditRequestDTO requestDTO, Long currentLoggedInUserId) {
        log.info("Officer ID {} is attempting to update Audit ID {}", currentLoggedInUserId, auditId);

        Audit existingAudit = auditRepository.findById(auditId)
                .orElseThrow(() -> new ResourceNotFoundException("Audit not found with ID: " + auditId));

        if (!existingAudit.getOfficerId().equals(currentLoggedInUserId)) {
            log.warn("SECURITY BLOCKED: Officer ID {} attempted to update Audit ID {} which they do not own.", currentLoggedInUserId, auditId);
            throw new UnauthorizedActionException("Access Denied: You can only edit your own audit reports.");
        }

        existingAudit.setScope(requestDTO.getScope());
        existingAudit.setFindings(requestDTO.getFindings());
        existingAudit.setStatus(requestDTO.getStatus());

        Audit updatedAudit = auditRepository.save(existingAudit);
        log.info("Successfully updated Audit ID {}", updatedAudit.getAuditId());

        return mapToResponseDTO(updatedAudit);
    }

    @Override
    public void deleteAudit(Long auditId, Long currentLoggedInUserId) {
        log.info("Officer ID {} is attempting to DELETE Audit ID {}", currentLoggedInUserId, auditId);

        Audit existingAudit = auditRepository.findById(auditId)
                .orElseThrow(() -> new ResourceNotFoundException("Audit not found with ID: " + auditId));

        if (!existingAudit.getOfficerId().equals(currentLoggedInUserId)) {
            log.warn("SECURITY BLOCKED: Officer ID {} attempted to delete Audit ID {} which they do not own.", currentLoggedInUserId, auditId);
            throw new UnauthorizedActionException("Access Denied: You can only delete your own audit reports.");
        }

        auditRepository.delete(existingAudit);
        log.info("Successfully deleted Audit ID {}", auditId);
    }

    private Audit mapToEntity(AuditRequestDTO dto) {
        return modelMapper.map(dto, Audit.class);
    }

    private AuditResponseDTO mapToResponseDTO(Audit entity) {
        return modelMapper.map(entity, AuditResponseDTO.class);
    }
}