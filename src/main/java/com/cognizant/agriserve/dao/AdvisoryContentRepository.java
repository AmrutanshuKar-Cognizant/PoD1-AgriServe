package com.cognizant.agriserve.dao;

import com.cognizant.agriserve.entity.AdvisoryContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvisoryContentRepository extends JpaRepository<AdvisoryContent, Integer> {
    // We will add findByStatus and findByCategory here later.
}
