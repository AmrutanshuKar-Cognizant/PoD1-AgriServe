package com.cognizant.agriserve.service;

import com.cognizant.agriserve.dto.AuditRequestDTO;
import com.cognizant.agriserve.dto.AuditResponseDTO;
import com.cognizant.agriserve.entity.Audit.AuditStatus;

import java.util.List;

public interface AuditService {

    AuditResponseDTO initiateAudit(AuditRequestDTO requestDTO);

    AuditResponseDTO getAuditById(Long auditId);

    List<AuditResponseDTO> getAllAudits();

    List<AuditResponseDTO> getAuditsByOfficerId(Long officerId);

    List<AuditResponseDTO> getAuditsByStatus(AuditStatus status);

    List<AuditResponseDTO> getAuditsByOfficerIdAndStatus(Long officerId, AuditStatus status);

    AuditResponseDTO updateAudit(Long auditId, AuditRequestDTO requestDTO);

    void deleteAudit(Long auditId);
}