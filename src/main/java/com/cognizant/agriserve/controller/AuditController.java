package com.cognizant.agriserve.controller;

import com.cognizant.agriserve.dto.request.AuditRequestDTO;
import com.cognizant.agriserve.dto.response.AuditResponseDTO;
import com.cognizant.agriserve.entity.Audit.AuditStatus;
import com.cognizant.agriserve.service.AuditService;
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
@RequestMapping("/api/audits")
@Validated
public class AuditController {

    private final AuditService auditService;

    public AuditController(AuditService auditService) {
        this.auditService = auditService;
    }

    @PostMapping
    public ResponseEntity<AuditResponseDTO> createAudit(
            @Valid @RequestBody AuditRequestDTO requestDTO) {

        log.info("API Request: Initiating a new Audit");

        AuditResponseDTO newAudit = auditService.initiateAudit(requestDTO);
        return new ResponseEntity<>(newAudit, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AuditResponseDTO>> getAllAudits() {
        log.info("API Request: Fetching all audits");
        return ResponseEntity.ok(auditService.getAllAudits());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuditResponseDTO> getAuditById(
            @PathVariable("id") @Positive(message = "ID must be greater than 0") Long auditId) {

        log.info("API Request: Fetching audit ID {}", auditId);
        return ResponseEntity.ok(auditService.getAuditById(auditId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuditResponseDTO> updateAudit(
            @PathVariable("id") @Positive(message = "ID must be greater than 0") Long auditId,
            @Valid @RequestBody AuditRequestDTO requestDTO) {

        log.info("API Request: Updating audit ID {}", auditId);
        AuditResponseDTO updatedAudit = auditService.updateAudit(auditId, requestDTO);
        return ResponseEntity.ok(updatedAudit);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAudit(
            @PathVariable("id") @Positive(message = "ID must be greater than 0") Long auditId) {

        log.info("API Request: Deleting audit ID {}", auditId);
        auditService.deleteAudit(auditId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/officer/{officerId}")
    public ResponseEntity<List<AuditResponseDTO>> getAuditsByOfficerId(
            @PathVariable @Positive(message = "ID must be greater than 0") Long officerId) {

        log.info("API Request: Fetching audits for Officer ID {}", officerId);
        return ResponseEntity.ok(auditService.getAuditsByOfficerId(officerId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<AuditResponseDTO>> getAuditsByStatus(@PathVariable AuditStatus status) {
        log.info("API Request: Fetching audits with status {}", status);
        return ResponseEntity.ok(auditService.getAuditsByStatus(status));
    }

    @GetMapping("/officer/{officerId}/status/{status}")
    public ResponseEntity<List<AuditResponseDTO>> getAuditsByOfficerIdAndStatus(
            @PathVariable @Positive(message = "ID must be greater than 0") Long officerId,
            @PathVariable AuditStatus status) {

        log.info("API Request: Fetching audits for Officer ID {} and status {}", officerId, status);
        return ResponseEntity.ok(auditService.getAuditsByOfficerIdAndStatus(officerId, status));
    }
}