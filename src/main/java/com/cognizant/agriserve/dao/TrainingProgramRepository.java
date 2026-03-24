package com.cognizant.agriserve.dao;

import com.cognizant.agriserve.entity.TrainingProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingProgramRepository extends JpaRepository<TrainingProgram, Long> {

    // Custom Method: Spring automatically writes the SQL to find programs by their status
    // Useful for showing farmers only "Scheduled" or "Active" programs
    List<TrainingProgram> findByStatus(String status);
}
