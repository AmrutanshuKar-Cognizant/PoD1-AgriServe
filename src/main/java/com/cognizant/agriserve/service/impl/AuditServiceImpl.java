package com.cognizant.agriserve.service.impl;

import com.cognizant.agriserve.dto.AuditRequestDTO;
import com.cognizant.agriserve.dto.AuditResponseDTO;
import com.cognizant.agriserve.entity.Audit;
import com.cognizant.agriserve.entity.Audit.AuditStatus;
import com.cognizant.agriserve.dao.AuditRepository;
import com.cognizant.agriserve.dao.UserRepository;
import com.cognizant.agriserve.entity.User;
import com.cognizant.agriserve.service.AuditService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import com.cognizant.agriserve.exception.ResourceNotFoundException;
import com.cognizant.agriserve.exception.UnauthorizedActionException;

@Service
public class AuditServiceImpl implements AuditService {

    private final AuditRepository auditRepository;
    private final UserRepository userRepository;


    public AuditServiceImpl(AuditRepository auditRepository, UserRepository userRepository) {
        this.auditRepository = auditRepository;
        this.userRepository = userRepository;
    }

    @Override
    public AuditResponseDTO initiateAudit(AuditRequestDTO requestDTO, Long currentLoggedInUserId) {
        Audit audit = mapToEntity(requestDTO);

        audit.setOfficerId(currentLoggedInUserId);

        Audit savedAudit = auditRepository.save(audit);
        return mapToResponseDTO(savedAudit);
    }

    @Override
    public AuditResponseDTO getAuditById(Long auditId) {
        Audit audit = auditRepository.findById(auditId)
                .orElseThrow(() -> new ResourceNotFoundException("Audit not found with ID: " + auditId));
        return mapToResponseDTO(audit);
    }

    @Override
    public List<AuditResponseDTO> getAllAudits() {
        return auditRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AuditResponseDTO> getAuditsByOfficerId(Long officerId) {
        // Note: You will need to add List<Audit> findByOfficerId(Long officerId) to your AuditRepository!
        return auditRepository.findByOfficerId(officerId).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AuditResponseDTO> getAuditsByStatus(AuditStatus status) {
        // Note: You will need to add List<Audit> findByStatus(AuditStatus status) to your AuditRepository!
        return auditRepository.findByStatus(status).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AuditResponseDTO> getAuditsByOfficerIdAndStatus(Long officerId, AuditStatus status) {
        return auditRepository.findByOfficerIdAndStatus(officerId, status).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AuditResponseDTO updateAudit(Long auditId, AuditRequestDTO requestDTO, Long currentLoggedInUserId) {
        Audit existingAudit = auditRepository.findById(auditId)
                .orElseThrow(() -> new ResourceNotFoundException("Audit not found with ID: " + auditId));

        // SECURITY CHECK: Bouncer kicks them out if they don't own it
        if (!existingAudit.getOfficerId().equals(currentLoggedInUserId)) {
            throw new UnauthorizedActionException("Access Denied: You can only edit your own audit reports.");
        }

        existingAudit.setScope(requestDTO.getScope());
        existingAudit.setFindings(requestDTO.getFindings());
        existingAudit.setStatus(requestDTO.getStatus());

        Audit updatedAudit = auditRepository.save(existingAudit);
        return mapToResponseDTO(updatedAudit);
    }

    @Override
    public void deleteAudit(Long auditId, Long currentLoggedInUserId) {
        Audit existingAudit = auditRepository.findById(auditId)
                .orElseThrow(() -> new ResourceNotFoundException("Audit not found with ID: " + auditId));

        // SECURITY CHECK: Bouncer protects deletions too
        if (!existingAudit.getOfficerId().equals(currentLoggedInUserId)) {
            throw new UnauthorizedActionException("Access Denied: You can only delete your own audit reports.");
        }

        auditRepository.delete(existingAudit);
    }

    // --- Private Helper Methods ---

    private Audit mapToEntity(AuditRequestDTO dto) {
        Audit audit = new Audit();
        audit.setScope(dto.getScope());
        audit.setFindings(dto.getFindings());
        audit.setStatus(dto.getStatus());
        // We do NOT set officerId here. It is handled securely in the service methods.
        return audit;
    }

    private AuditResponseDTO mapToResponseDTO(Audit entity) {
        AuditResponseDTO dto = new AuditResponseDTO();
        dto.setAuditId(entity.getAuditId());
        dto.setOfficerId(entity.getOfficerId());

        String realOfficerName = userRepository.findById(entity.getOfficerId().intValue())
                .map(User::getName)
                .orElse("Unknown Officer");

        dto.setOfficerName(realOfficerName); // Inject the real name into the DTO

        dto.setScope(entity.getScope());
        dto.setFindings(entity.getFindings());
        dto.setDate(entity.getDate());
        dto.setStatus(entity.getStatus());

        return dto;
    }
}