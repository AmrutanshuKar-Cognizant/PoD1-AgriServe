package com.cognizant.agriserve.dao;

import com.cognizant.agriserve.entity.Farmer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FarmerRepository extends JpaRepository<Farmer, Long>
    {

    }

