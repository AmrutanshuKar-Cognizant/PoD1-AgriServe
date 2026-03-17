package com.cognizant.agriserve.service;

import com.cognizant.agriserve.dao.AuditLogRepository;
import com.cognizant.agriserve.entity.AuditLog;
import com.cognizant.agriserve.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuditLogService {

    @Autowired
    private AuditLogRepository auditLogRepository;

    // Create a new audit log entry
    public AuditLog logAction(User user, String action, String resource) {

        AuditLog auditLog = new AuditLog();
        auditLog.setUser(user);
        auditLog.setAction(action);
        auditLog.setResource(resource);
        auditLog.setTimestamp(LocalDateTime.now());

        return auditLogRepository.save(auditLog);
    }

    // Get audit log by ID
    public AuditLog getAuditLogById(Integer auditId) {
        return auditLogRepository.findById(auditId)
                .orElseThrow(() -> new RuntimeException("Audit log not found"));
    }

    // Get all audit logs
    public List<AuditLog> getAllAuditLogs() {
        return auditLogRepository.findAll();
    }

    // Get audit logs for a specific user
    public List<AuditLog> getAuditLogsByUser(User user) {
        return auditLogRepository.findByUser(user);
    }

    // Get audit logs by action type
    public List<AuditLog> getAuditLogsByAction(String action) {
        return auditLogRepository.findByAction(action);
    }

    // Get audit logs by resource
    public List<AuditLog> getAuditLogsByResource(String resource) {
        return auditLogRepository.findByResource(resource);
    }

    // Get audit logs within a time range
    public List<AuditLog> getAuditLogsByDateRange(
            LocalDateTime startTime,
            LocalDateTime endTime) {

        return auditLogRepository.findByTimestampBetween(startTime, endTime);
    }

    // Get audit logs for a user within a time range
    public List<AuditLog> getAuditLogsByUserAndDateRange(
            User user,
            LocalDateTime startTime,
            LocalDateTime endTime) {

        return auditLogRepository.findByUserAndTimestampBetween(
                user, startTime, endTime);
    }
}
