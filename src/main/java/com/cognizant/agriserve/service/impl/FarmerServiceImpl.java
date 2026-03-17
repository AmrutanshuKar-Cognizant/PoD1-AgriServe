package com.cognizant.agriserve.service.impl;

import com.cognizant.agriserve.dao.FarmerRepository;
import com.cognizant.agriserve.entity.Farmer;

import com.cognizant.agriserve.service.FarmerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FarmerServiceImpl implements FarmerService {

    @Autowired
    private FarmerRepository farmerRepository;

    @Override
    public Farmer createFarmer(Farmer farmer)
    {
        farmer.setStatus("Pending");
        return farmerRepository.save(farmer);
    }

    @Override
    public Optional<Farmer> getFarmerById(Long farmerId)
    {
        return farmerRepository.findById(farmerId);
    }

}
