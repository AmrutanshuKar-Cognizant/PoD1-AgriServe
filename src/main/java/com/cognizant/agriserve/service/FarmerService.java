package com.cognizant.agriserve.service;

import com.cognizant.agriserve.entity.Farmer;

import java.util.List;
import java.util.Optional;

public interface FarmerService {
    Farmer createFarmer(Farmer farmer);

    Optional<Farmer> getFarmerById(Long farmerId);


}