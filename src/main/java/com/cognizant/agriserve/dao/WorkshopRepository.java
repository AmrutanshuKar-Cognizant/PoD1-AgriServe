package com.cognizant.agriserve.dao;

import com.cognizant.agriserve.entity.Workshop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface WorkshopRepository extends JpaRepository<Workshop, Long> {


    List<Workshop> findByOfficerId(Long officerId);

    List<Workshop> findByTrainingProgram_ProgramIdAndDateAfter(Long programId, LocalDateTime date);
}
