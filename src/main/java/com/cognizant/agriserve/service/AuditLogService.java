package com.cognizant.agriserve.service;

import com.cognizant.agriserve.dto.AuditLogResponseDTO;
import com.cognizant.agriserve.entity.User;
import com.cognizant.agriserve.exception.AuditLogNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

public interface AuditLogService {

    void logAction(User user, String action, String resource);

    // Get audit log by ID
    AuditLogResponseDTO getAuditLogById(Integer auditId) throws AuditLogNotFoundException;

    // Get all audit logs
    List<AuditLogResponseDTO> getAllAuditLogs();

    // Get audit logs for a specific user
    List<AuditLogResponseDTO> getAuditLogsByUser(User user);

    // Get audit logs by action type
    List<AuditLogResponseDTO> getAuditLogsByAction(String action);

    // Get audit logs by resource
    List<AuditLogResponseDTO> getAuditLogsByResource(String resource);

    // Get audit logs within a date range
    List<AuditLogResponseDTO> getAuditLogsByDateRange(

            LocalDateTime startTime,

            LocalDateTime endTime

    );

    // Get audit logs for a user within a date range
    List<AuditLogResponseDTO> getAuditLogsByUserAndDateRange(

            User user,
            LocalDateTime startTime,
            LocalDateTime endTime

    );

}
