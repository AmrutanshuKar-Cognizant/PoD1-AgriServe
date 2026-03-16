package com.cognizant.agriserve.dao;

import com.cognizant.agriserve.entity.ComplianceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComplianceRecordRepository extends JpaRepository<ComplianceRecord, Long> {

    // Fetch all compliance records for a specific session or program
    List<ComplianceRecord> findByEntityId(Long entityId);

    // Fetch records by their specific type (ADVISORY or TRAINING)
    List<ComplianceRecord> findByType(ComplianceRecord.ComplianceType type);

    // Fetch records for a specific entity AND a specific type
    List<ComplianceRecord> findByEntityIdAndType(Long entityId, ComplianceRecord.ComplianceType type);
}