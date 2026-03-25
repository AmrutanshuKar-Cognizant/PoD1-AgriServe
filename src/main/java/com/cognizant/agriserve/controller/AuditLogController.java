package com.cognizant.agriserve.controller;

import com.cognizant.agriserve.dto.response.AuditLogResponseDTO;
import com.cognizant.agriserve.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/audit-logs")
@RequiredArgsConstructor // Replaces @Autowired for cleaner constructor injection

public class AuditLogController {
    private final AuditLogService auditLogService;
    // Notice we removed UserService completely! The controller doesn't need it.
    // Get all audit logs (Admin / Auditor)
    @GetMapping

    public ResponseEntity<List<AuditLogResponseDTO>> getAllAuditLogs() {
        log.info("API Request: Fetching all audit logs");
        return ResponseEntity.ok(auditLogService.getAllAuditLogs());
    }

    // Get audit log by ID (Admin / Auditor)
    @GetMapping("/{id}")
    public ResponseEntity<AuditLogResponseDTO> getAuditLogById(@PathVariable Integer id) {
        log.info("API Request: Fetching audit log with ID: {}", id);
        return ResponseEntity.ok(auditLogService.getAuditLogById(id));

    }
    // Get audit logs by action
    @GetMapping("/action/{action}")

    public ResponseEntity<List<AuditLogResponseDTO>> getAuditLogsByAction(@PathVariable String action) {
        log.info("API Request: Fetching audit logs for action: {}", action);
        return ResponseEntity.ok(auditLogService.getAuditLogsByAction(action));

    }

    // Get audit logs by resource

    @GetMapping("/resource/{resource}")

    public ResponseEntity<List<AuditLogResponseDTO>> getAuditLogsByResource(@PathVariable String resource) {

        log.info("API Request: Fetching audit logs for resource: {}", resource);
        return ResponseEntity.ok(auditLogService.getAuditLogsByResource(resource));

    }

    // Get audit logs within date range

    @GetMapping("/date-range")

    public ResponseEntity<List<AuditLogResponseDTO>> getAuditLogsByDateRange(

            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {

        log.info("API Request: Fetching audit logs between {} and {}", start, end);
        return ResponseEntity.ok(auditLogService.getAuditLogsByDateRange(start, end));

    }

}
