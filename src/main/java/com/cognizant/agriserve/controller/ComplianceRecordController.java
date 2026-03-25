package com.cognizant.agriserve.controller;

import com.cognizant.agriserve.dto.ComplianceRecordRequestDTO;
import com.cognizant.agriserve.dto.ComplianceRecordResponseDTO;
import com.cognizant.agriserve.entity.ComplianceRecord.ComplianceType;
import com.cognizant.agriserve.service.ComplianceRecordService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize; // Added for Role Security
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/compliance-records")
@Validated
public class ComplianceRecordController {

    private final ComplianceRecordService complianceRecordService;

    public ComplianceRecordController(ComplianceRecordService complianceRecordService) {
        this.complianceRecordService = complianceRecordService;
    }


    @PostMapping
    public ResponseEntity<ComplianceRecordResponseDTO> createRecord(
            @Valid @RequestBody ComplianceRecordRequestDTO requestDTO) {

        log.info("API Request: Creating a new Compliance Record");

        ComplianceRecordResponseDTO newRecord = complianceRecordService.createComplianceRecord(requestDTO);
        return new ResponseEntity<>(newRecord, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ComplianceRecordResponseDTO>> getAllRecords() {
        log.info("API Request: Fetching all compliance records");
        return ResponseEntity.ok(complianceRecordService.getAllComplianceRecords());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComplianceRecordResponseDTO> getRecordById(
            @PathVariable("id") @Positive(message = "ID must be greater than 0") Long complianceId) {

        log.info("API Request: Fetching compliance record ID {}", complianceId);
        return ResponseEntity.ok(complianceRecordService.getComplianceRecordById(complianceId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ComplianceRecordResponseDTO> updateRecord(
            @PathVariable("id") @Positive(message = "ID must be greater than 0") Long complianceId,
            @Valid @RequestBody ComplianceRecordRequestDTO requestDTO) {

        log.info("API Request: Updating compliance record ID {}", complianceId);
        ComplianceRecordResponseDTO updatedRecord = complianceRecordService.updateComplianceRecord(complianceId, requestDTO);
        return ResponseEntity.ok(updatedRecord);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecord(
            @PathVariable("id") @Positive(message = "ID must be greater than 0") Long complianceId) {

        log.info("API Request: Deleting compliance record ID {}", complianceId);
        complianceRecordService.deleteComplianceRecord(complianceId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/entity/{entityId}")
    public ResponseEntity<List<ComplianceRecordResponseDTO>> getRecordsByEntity(
            @PathVariable @Positive(message = "ID must be greater than 0") Long entityId) {

        log.info("API Request: Fetching compliance records for Entity ID {}", entityId);
        return ResponseEntity.ok(complianceRecordService.getRecordsByEntity(entityId));
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<ComplianceRecordResponseDTO>> getRecordsByType(@PathVariable ComplianceType type) {
        log.info("API Request: Fetching compliance records of type {}", type);
        return ResponseEntity.ok(complianceRecordService.getRecordsByType(type));
    }

    @GetMapping("/entity/{entityId}/type/{type}")
    public ResponseEntity<List<ComplianceRecordResponseDTO>> getRecordsByEntityAndType(
            @PathVariable @Positive(message = "ID must be greater than 0") Long entityId,
            @PathVariable ComplianceType type) {

        log.info("API Request: Fetching compliance records for Entity ID {} and type {}", entityId, type);
        return ResponseEntity.ok(complianceRecordService.getRecordsByEntityAndType(entityId, type));
    }
}