package com.cognizant.agriserve.controller;

import com.cognizant.agriserve.dto.ComplianceRecordRequestDTO;
import com.cognizant.agriserve.dto.ComplianceRecordResponseDTO;
import com.cognizant.agriserve.entity.ComplianceRecord.ComplianceType;
import com.cognizant.agriserve.service.ComplianceRecordService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compliance-records")
@Validated
public class ComplianceRecordController {

    private final ComplianceRecordService complianceRecordService;

    public ComplianceRecordController(ComplianceRecordService complianceRecordService) {
        this.complianceRecordService = complianceRecordService;
    }

    // CREATE A RECORD
    @PostMapping
    public ResponseEntity<ComplianceRecordResponseDTO> createRecord(
            @Valid @RequestBody ComplianceRecordRequestDTO requestDTO,
            @RequestHeader("User-Id") Long currentLoggedInUserId) {

        ComplianceRecordResponseDTO newRecord = complianceRecordService.createComplianceRecord(requestDTO, currentLoggedInUserId);
        return new ResponseEntity<>(newRecord, HttpStatus.CREATED);
    }

    // GET ALL RECORDS
    @GetMapping
    public ResponseEntity<List<ComplianceRecordResponseDTO>> getAllRecords() {
        return ResponseEntity.ok(complianceRecordService.getAllComplianceRecords());
    }

    // GET A SINGLE RECORD BY ID
    @GetMapping("/{id}")
    public ResponseEntity<ComplianceRecordResponseDTO> getRecordById(@PathVariable("id") @Positive(message = "ID must be greater than 0") Long complianceId) {
        return ResponseEntity.ok(complianceRecordService.getComplianceRecordById(complianceId));
    }

    // UPDATE A RECORD
    @PutMapping("/{id}")
    public ResponseEntity<ComplianceRecordResponseDTO> updateRecord(
            @PathVariable("id") @Positive(message = "ID must be greater than 0") Long complianceId,
            @Valid @RequestBody ComplianceRecordRequestDTO requestDTO,
            @RequestHeader("User-Id") Long currentLoggedInUserId) {

        ComplianceRecordResponseDTO updatedRecord = complianceRecordService.updateComplianceRecord(complianceId, requestDTO, currentLoggedInUserId);
        return ResponseEntity.ok(updatedRecord);
    }

    // DELETE A RECORD
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecord(
            @PathVariable("id") @Positive(message = "ID must be greater than 0") Long complianceId,
            @RequestHeader("User-Id") Long currentLoggedInUserId) {

        complianceRecordService.deleteComplianceRecord(complianceId, currentLoggedInUserId);
        return ResponseEntity.noContent().build();
    }


    // GET RECORDS BY ENTITY ID
    @GetMapping("/entity/{entityId}")
    public ResponseEntity<List<ComplianceRecordResponseDTO>> getRecordsByEntity(@PathVariable @Positive(message = "ID must be greater than 0") Long entityId) {
        return ResponseEntity.ok(complianceRecordService.getRecordsByEntity(entityId));
    }

    // GET RECORDS BY TYPE
    @GetMapping("/type/{type}")
    public ResponseEntity<List<ComplianceRecordResponseDTO>> getRecordsByType(@PathVariable ComplianceType type) {
        return ResponseEntity.ok(complianceRecordService.getRecordsByType(type));
    }

    // GET RECORDS BY ENTITY AND TYPE
    @GetMapping("/entity/{entityId}/type/{type}")
    public ResponseEntity<List<ComplianceRecordResponseDTO>> getRecordsByEntityAndType(
            @PathVariable @Positive(message = "ID must be greater than 0") Long entityId,
            @PathVariable ComplianceType type) {

        return ResponseEntity.ok(complianceRecordService.getRecordsByEntityAndType(entityId, type));
    }
}