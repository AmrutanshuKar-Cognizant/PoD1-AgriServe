package com.cognizant.agriserve.dao;

import com.cognizant.agriserve.entity.Audit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditRepository extends JpaRepository<Audit, Long> {

    // Fetch all audits conducted by a specific compliance officer
    List<Audit> findByOfficerId(Long officerId);

    // Fetch all audits based on their current status (PENDING, IN_PROGRESS, COMPLETED)
    List<Audit> findByStatus(Audit.AuditStatus status);

    // Fetch audits by both officer and status (e.g., all PENDING audits for Officer #5)
    List<Audit> findByOfficerIdAndStatus(Long officerId, Audit.AuditStatus status);
}