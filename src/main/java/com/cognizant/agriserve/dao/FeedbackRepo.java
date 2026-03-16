package com.cognizant.agriserve.dao;

import com.cognizant.agriserve.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepo extends JpaRepository<Feedback,Integer> {
}
