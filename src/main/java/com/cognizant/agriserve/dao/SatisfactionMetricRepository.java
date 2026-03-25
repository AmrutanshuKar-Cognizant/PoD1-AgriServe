package com.cognizant.agriserve.dao;

import com.cognizant.agriserve.entity.SatisfactionMetric;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SatisfactionMetricRepository extends JpaRepository<SatisfactionMetric, Integer> {
    boolean existsByTrainingProgram_ProgramId(Integer programId);
}