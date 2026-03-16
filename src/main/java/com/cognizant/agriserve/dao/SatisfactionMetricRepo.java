package com.cognizant.agriserve.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cognizant.agriserve.entity.SatisfactionMetric;
public interface SatisfactionMetricRepo extends JpaRepository<SatisfactionMetric,Integer> {

}
