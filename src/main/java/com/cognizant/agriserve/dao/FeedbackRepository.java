package com.cognizant.agriserve.dao;

import com.cognizant.agriserve.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
    List<Feedback> findByTrainingProgram_ProgramId(Long programId);
}