package com.cognizant.agriserve.service.impl;

import com.cognizant.agriserve.dao.FarmerRepository;
import com.cognizant.agriserve.dto.FarmerDTO;
import com.cognizant.agriserve.dto.FarmerUpdateRequestDTO;
import com.cognizant.agriserve.entity.Farmer;
import com.cognizant.agriserve.exception.ResourceNotFoundException;
import com.cognizant.agriserve.service.FarmerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.modelmapper.ModelMapper; // Added ModelMapper import

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FarmerServiceImpl implements FarmerService {

    // dependencies
    private final FarmerRepository farmerRepository;
    private final ModelMapper modelMapper; // Injected ModelMapper

    // Method 1: getFarmerProfile
    @Override
    public FarmerDTO getFarmerProfile(String email) {

        log.debug("Fetching farmer profile for user email: {}", email);

        // if farmer profile is not found throws exception
        Farmer farmer = farmerRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Farmer profile not found for email: " + email));

        // if farmer profile found means it convert to DTO
        return mapToDto(farmer);

    }

    // Method 2: updateFarmerProfile
    @Override
    @Transactional  // ensure safe DB update

    public FarmerDTO updateFarmerProfile(String email, FarmerUpdateRequestDTO updateDto) {

        log.info("Updating farmer profile for user email: {}", email);

        // 1. find farmer
        Farmer existingFarmer = farmerRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Farmer profile not found for email: " + email));

        // 2. automatically maps name, address, landsize and all
        modelMapper.map(updateDto, existingFarmer);

        // 3. handle special fields convert string -> Date
        existingFarmer.setDob(LocalDate.parse(updateDto.getDob()));

        // convert string -> enum
        existingFarmer.setGender(Farmer.Gender.valueOf(updateDto.getGender().toUpperCase()));

        Farmer updatedFarmer = farmerRepository.save(existingFarmer); // DAO saves the data

        log.info("Successfully updated profile for farmer ID: {}", updatedFarmer.getFarmerId());

        return mapToDto(updatedFarmer); // return to DTO

    }

    // Method 3: getAllFarmers()
    @Override
    // returns list of FarmerDTO
    public List<FarmerDTO> getAllFarmers() {

        log.debug("Fetching all farmers from the database"); // prints in console

        // farmerRepository.findAll() - fetch all farmer from DB [ SELECT * FROM  farmer ]
        // .stream() - after fetching the list converts to stream so we can process each farmer
        // .map(this::mapToDto) - for each farmer convert entity -> DTO
        return farmerRepository.findAll().stream()

                .map(this::mapToDto)// convert to DTO
                .collect(Collectors.toList());

    }


    // entity -> FarmerDTO
    // bcoz never send entity to frontend directly
    // use DTO for security and clean response
    private FarmerDTO mapToDto(Farmer farmer) {



        return modelMapper.map(farmer, FarmerDTO.class);

    }

}
