package com.cognizant.agriserve.dao;

import com.cognizant.agriserve.entity.AdvisoryContent;
import com.cognizant.agriserve.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AdvisoryContentRepository extends JpaRepository<AdvisoryContent, Long> {

    // For P1A-28: Allows the Officer to see only current, non-deleted materials
    List<AdvisoryContent> findByStatus(String status);

    // For P1A-28: Allows filtering the library by topic (e.g., 'CROP' or 'SOIL')
    List<AdvisoryContent> findByCategoryAndStatus(String category, String status);

    List<AdvisoryContent> findByUploadedBy(User user);
}