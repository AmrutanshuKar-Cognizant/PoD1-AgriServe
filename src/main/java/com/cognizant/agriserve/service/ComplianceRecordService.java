package com.cognizant.agriserve.service;

import com.cognizant.agriserve.dto.ComplianceRecordRequestDTO;
import com.cognizant.agriserve.dto.ComplianceRecordResponseDTO;
import com.cognizant.agriserve.entity.ComplianceRecord.ComplianceType;

import java.util.List;

public interface ComplianceRecordService {

    ComplianceRecordResponseDTO createComplianceRecord(ComplianceRecordRequestDTO requestDTO);

    ComplianceRecordResponseDTO getComplianceRecordById(Long complianceId);

    List<ComplianceRecordResponseDTO> getAllComplianceRecords();

    List<ComplianceRecordResponseDTO> getRecordsByEntity(Long entityId);

    List<ComplianceRecordResponseDTO> getRecordsByType(ComplianceType type);

    List<ComplianceRecordResponseDTO> getRecordsByEntityAndType(Long entityId, ComplianceType type);

    ComplianceRecordResponseDTO updateComplianceRecord(Long complianceId, ComplianceRecordRequestDTO requestDTO);

    void deleteComplianceRecord(Long complianceId);
}