package com.cognizant.agriserve.dao;

import com.cognizant.agriserve.entity.AuditLog;
import com.cognizant.agriserve.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Integer> {

    // Find all audit logs by user
    List<AuditLog> findByUser(User user);

    // Find audit logs by action type
    List<AuditLog> findByAction(String action);

    // Find audit logs for a specific resource
    List<AuditLog> findByResource(String resource);

    // Find audit logs within a time range
    List<AuditLog> findByTimestampBetween(
            LocalDateTime startTime,
            LocalDateTime endTime
    );

    // Find audit logs by user and time range
    List<AuditLog> findByUserAndTimestampBetween(
            User user,
            LocalDateTime startTime,
            LocalDateTime endTime
    );
}