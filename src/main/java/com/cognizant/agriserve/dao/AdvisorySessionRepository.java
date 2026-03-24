package com.cognizant.agriserve.dao;

import com.cognizant.agriserve.entity.AdvisorySession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

@Repository
public interface AdvisorySessionRepository extends JpaRepository<AdvisorySession, Long> {

    // For P1A-30: Retrieve the history of advice given to a specific farmer
    List<AdvisorySession> findByFarmer_FarmerId(Long farmerId);

    // For P1A-31: Track Advisory Usage (The Manager's Report)
    // This Native Query counts sessions grouped by ContentID
    @Query(value = "SELECT content_id, COUNT(*) as usage_count FROM advisory_session GROUP BY content_id", nativeQuery = true)
    List<Map<String, Object>> getContentUsageReport();

    // To see which content is popular in a specific category
    @Query("SELECT s.content.title, COUNT(s) FROM AdvisorySession s GROUP BY s.content.title")
    List<Object[]> countUsageByTitle();
}