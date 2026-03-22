package com.cognizant.agriserve.dao;

import com.cognizant.agriserve.entity.Participation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipationRepository extends JpaRepository<Participation, Long> {

    // Custom Method: Finds all farmers registered for a specific workshop
    // You will use this to load the list for Attendance Tracking
    List<Participation> findByWorkshop_WorkshopId(Long workshopId);

    // Custom Method: Checks if a specific farmer is already registered for a workshop
    boolean existsByWorkshop_WorkshopIdAndFarmerId(Long workshopId, Long farmerId);
}
