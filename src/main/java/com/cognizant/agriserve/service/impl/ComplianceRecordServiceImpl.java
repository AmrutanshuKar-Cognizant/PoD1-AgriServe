package com.cognizant.agriserve.service.impl;

import com.cognizant.agriserve.dto.ComplianceRecordRequestDTO;
import com.cognizant.agriserve.dto.ComplianceRecordResponseDTO;
import com.cognizant.agriserve.entity.ComplianceRecord;
import com.cognizant.agriserve.entity.ComplianceRecord.ComplianceType;
import com.cognizant.agriserve.dao.ComplianceRecordRepository;
import com.cognizant.agriserve.service.ComplianceRecordService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import com.cognizant.agriserve.exception.ResourceNotFoundException;
import com.cognizant.agriserve.exception.UnauthorizedActionException;

@Service
public class ComplianceRecordServiceImpl implements ComplianceRecordService {

    private final ComplianceRecordRepository complianceRecordRepository;

    public ComplianceRecordServiceImpl(ComplianceRecordRepository complianceRecordRepository) {
        this.complianceRecordRepository = complianceRecordRepository;
    }

    @Override
    public ComplianceRecordResponseDTO createComplianceRecord(ComplianceRecordRequestDTO requestDTO, Long currentLoggedInUserId) {
        ComplianceRecord complianceRecord = mapToEntity(requestDTO);

        complianceRecord.setOfficerId(currentLoggedInUserId);

        ComplianceRecord savedRecord = complianceRecordRepository.save(complianceRecord);
        return mapToResponseDTO(savedRecord);
    }

    @Override
    public ComplianceRecordResponseDTO getComplianceRecordById(Long complianceId) {
        ComplianceRecord record = complianceRecordRepository.findById(complianceId)
                .orElseThrow(() -> new ResourceNotFoundException("Compliance Record not found with ID: " + complianceId));
        return mapToResponseDTO(record);
    }

    @Override
    public List<ComplianceRecordResponseDTO> getAllComplianceRecords() {
        return complianceRecordRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ComplianceRecordResponseDTO> getRecordsByEntity(Long entityId) {
        return complianceRecordRepository.findByEntityId(entityId).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ComplianceRecordResponseDTO> getRecordsByType(ComplianceType type) {
        return complianceRecordRepository.findByType(type).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ComplianceRecordResponseDTO> getRecordsByEntityAndType(Long entityId, ComplianceType type) {
        return complianceRecordRepository.findByEntityIdAndType(entityId, type).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ComplianceRecordResponseDTO updateComplianceRecord(Long complianceId, ComplianceRecordRequestDTO requestDTO, Long currentLoggedInUserId) {
        ComplianceRecord existingRecord = complianceRecordRepository.findById(complianceId)
                .orElseThrow(() -> new ResourceNotFoundException("Compliance Record not found with ID: " + complianceId));

        // SECURITY CHECK: Bouncer kicks them out if they don't own it
        if (!existingRecord.getOfficerId().equals(currentLoggedInUserId)) {
            throw new UnauthorizedActionException("Access Denied: You can only edit your own compliance records.");
        }

        existingRecord.setEntityId(requestDTO.getEntityId());
        existingRecord.setType(requestDTO.getType());
        existingRecord.setResult(requestDTO.getResult());
        existingRecord.setNotes(requestDTO.getNotes());

        ComplianceRecord updatedRecord = complianceRecordRepository.save(existingRecord);
        return mapToResponseDTO(updatedRecord);
    }

    @Override
    public void deleteComplianceRecord(Long complianceId, Long currentLoggedInUserId) {
        ComplianceRecord existingRecord = complianceRecordRepository.findById(complianceId)
                .orElseThrow(() -> new ResourceNotFoundException("Compliance Record not found with ID: " + complianceId));

        if (!existingRecord.getOfficerId().equals(currentLoggedInUserId)) {
            throw new UnauthorizedActionException("Access Denied: You can only delete your own compliance records.");
        }

        complianceRecordRepository.delete(existingRecord);
    }


    private ComplianceRecord mapToEntity(ComplianceRecordRequestDTO dto) {
        ComplianceRecord record = new ComplianceRecord();
        record.setEntityId(dto.getEntityId());
        record.setType(dto.getType());
        record.setResult(dto.getResult());
        record.setNotes(dto.getNotes());
        return record;
    }

    private ComplianceRecordResponseDTO mapToResponseDTO(ComplianceRecord entity) {
        ComplianceRecordResponseDTO dto = new ComplianceRecordResponseDTO();
        dto.setComplianceId(entity.getComplianceId());
        dto.setEntityId(entity.getEntityId());
        dto.setOfficerId(entity.getOfficerId());
        dto.setType(entity.getType());
        dto.setResult(entity.getResult());
        dto.setDate(entity.getDate());
        dto.setNotes(entity.getNotes());
        return dto;
    }
}