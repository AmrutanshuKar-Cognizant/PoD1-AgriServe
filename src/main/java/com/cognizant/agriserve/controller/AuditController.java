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

    // INITIATE AN AUDIT
    @PostMapping
    public ResponseEntity<AuditResponseDTO> createAudit(
            @Valid @RequestBody AuditRequestDTO requestDTO,
            @RequestHeader("User-Id") Long currentLoggedInUserId) {

        log.info("API Request: User {} is initiating a new Audit", currentLoggedInUserId);
        AuditResponseDTO newAudit = auditService.initiateAudit(requestDTO, currentLoggedInUserId);
        return new ResponseEntity<>(newAudit, HttpStatus.CREATED);
    }

    // GET ALL AUDITS
    @GetMapping
    public ResponseEntity<List<AuditResponseDTO>> getAllAudits() {
        log.info("API Request: Fetching all audits");
        return ResponseEntity.ok(auditService.getAllAudits());
    }

    // GET A SINGLE AUDIT BY ID
    @GetMapping("/{id}")
    public ResponseEntity<AuditResponseDTO> getAuditById(
            @PathVariable("id") @Positive(message = "ID must be greater than 0") Long auditId) {

        log.info("API Request: Fetching audit ID {}", auditId);
        return ResponseEntity.ok(auditService.getAuditById(auditId));
    }

    // UPDATE AN AUDIT
    @PutMapping("/{id}")
    public ResponseEntity<AuditResponseDTO> updateAudit(
            @PathVariable("id") @Positive(message = "ID must be greater than 0") Long auditId,
            @Valid @RequestBody AuditRequestDTO requestDTO,
            @RequestHeader("User-Id") Long currentLoggedInUserId) {

        log.info("API Request: User {} is updating audit ID {}", currentLoggedInUserId, auditId);
        AuditResponseDTO updatedAudit = auditService.updateAudit(auditId, requestDTO, currentLoggedInUserId);
        return ResponseEntity.ok(updatedAudit);
    }

    // DELETE AN AUDIT
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAudit(
            @PathVariable("id") @Positive(message = "ID must be greater than 0") Long auditId,
            @RequestHeader("User-Id") Long currentLoggedInUserId) {

        log.info("API Request: User {} is deleting audit ID {}", currentLoggedInUserId, auditId);
        auditService.deleteAudit(auditId, currentLoggedInUserId);
        return ResponseEntity.noContent().build();
    }

    // GET AUDITS BY OFFICER ID
    @GetMapping("/officer/{officerId}")
    public ResponseEntity<List<AuditResponseDTO>> getAuditsByOfficerId(
            @PathVariable @Positive(message = "ID must be greater than 0") Long officerId) {

        log.info("API Request: Fetching audits for Officer ID {}", officerId);
        return ResponseEntity.ok(auditService.getAuditsByOfficerId(officerId));
    }

    // GET AUDITS BY STATUS
    @GetMapping("/status/{status}")
    public ResponseEntity<List<AuditResponseDTO>> getAuditsByStatus(@PathVariable AuditStatus status) {
        log.info("API Request: Fetching audits with status {}", status);
        return ResponseEntity.ok(auditService.getAuditsByStatus(status));
    }

    // GET AUDITS BY OFFICER ID AND STATUS
    @GetMapping("/officer/{officerId}/status/{status}")
    public ResponseEntity<List<AuditResponseDTO>> getAuditsByOfficerIdAndStatus(
            @PathVariable @Positive(message = "ID must be greater than 0") Long officerId,
            @PathVariable AuditStatus status) {

        log.info("API Request: Fetching audits for Officer ID {} and status {}", officerId, status);
        return ResponseEntity.ok(auditService.getAuditsByOfficerIdAndStatus(officerId, status));
    }
}