package com.cognizant.agriserve.service;

import com.cognizant.agriserve.dto.FarmerDTO;
import com.cognizant.agriserve.dto.request.FarmerUpdateRequestDTO;

import java.util.List;

public interface FarmerService {
    // For the logged-in Farmer to view their own profile
    FarmerDTO getFarmerProfile(String email);

    // For the logged-in Farmer to update their own profile
    FarmerDTO updateFarmerProfile(String email, FarmerUpdateRequestDTO updateDto);

    // For Extension Officers or Admins to view all farmers
    List<FarmerDTO> getAllFarmers();
}