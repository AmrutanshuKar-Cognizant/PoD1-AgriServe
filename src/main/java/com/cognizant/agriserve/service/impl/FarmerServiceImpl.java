package com.cognizant.agriserve.service.impl;

import com.cognizant.agriserve.dao.FarmerRepository;
import com.cognizant.agriserve.dto.FarmerDTO;
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
    public Farmer createFarmer(FarmerDTO dto)
    {
        Farmer farmer=new Farmer();

        farmer.setName(dto.getName());
        farmer.setDob(dto.getDob());
        farmer.setGender(dto.getGender());
        farmer.setAddress(dto.getAddress());
        farmer.setContactInfo(dto.getContactInfo());
        farmer.setLandSize(dto.getLandSize());
        farmer.setCropType(dto.getCropType());

        farmer.setStatus("Pending");  //backend control

        return farmerRepository.save(farmer);
    }

    @Override
    public Optional<Farmer> getFarmerById(Long farmerId)
    {
        return farmerRepository.findById(farmerId);
    }

}
