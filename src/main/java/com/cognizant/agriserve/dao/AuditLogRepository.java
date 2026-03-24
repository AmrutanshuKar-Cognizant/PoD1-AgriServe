package com.cognizant.agriserve.dao;

import com.cognizant.agriserve.entity.AuditLog;
import com.cognizant.agriserve.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Integer> {


    List<AuditLog> findByUser(User user);

    List<AuditLog> findByAction(String action);

    List<AuditLog> findByResource(String resource);

    List<AuditLog> findByTimestampBetween(
            LocalDateTime startTime,
            LocalDateTime endTime
    );

    List<AuditLog> findByUserAndTimestampBetween(
            User user,
            LocalDateTime startTime,
            LocalDateTime endTime
    );
}