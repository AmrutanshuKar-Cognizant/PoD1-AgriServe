package com.cognizant.agriserve.dao;

import com.cognizant.agriserve.entity.AdvisorySession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvisorySessionRepository extends JpaRepository<AdvisorySession, Integer> {
    // We will add the @Query for usage reports and farmer history here later.
}
