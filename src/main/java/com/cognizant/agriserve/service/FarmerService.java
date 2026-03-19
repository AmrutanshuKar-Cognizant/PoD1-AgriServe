package com.cognizant.agriserve.service;

import com.cognizant.agriserve.dto.FarmerDTO;
import com.cognizant.agriserve.entity.Farmer;

import java.util.List;
import java.util.Optional;

public interface FarmerService {
    Farmer createFarmer(FarmerDTO dto);

    Farmer updateFarmer(Long farmerId, FarmerDTO dto);

    Optional<Farmer> getFarmerById(Long farmerId);


}