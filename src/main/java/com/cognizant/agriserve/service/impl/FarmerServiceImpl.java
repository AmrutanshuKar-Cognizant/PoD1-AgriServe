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

    private final FarmerRepository farmerRepository;

    private final ModelMapper modelMapper; // Injected ModelMapper

    @Override

    public FarmerDTO getFarmerProfile(String email) {

        log.debug("Fetching farmer profile for user email: {}", email);

        Farmer farmer = farmerRepository.findByEmail(email)

                .orElseThrow(() -> new ResourceNotFoundException("Farmer profile not found for email: " + email));

        return mapToDto(farmer);

    }

    @Override

    @Transactional

    public FarmerDTO updateFarmerProfile(String email, FarmerUpdateRequestDTO updateDto) {

        log.info("Updating farmer profile for user email: {}", email);

        Farmer existingFarmer = farmerRepository.findByEmail(email)

                .orElseThrow(() -> new ResourceNotFoundException("Farmer profile not found for email: " + email));

        // 1. Let ModelMapper map all the standard Strings and Doubles automatically

        modelMapper.map(updateDto, existingFarmer);

        // 2. Safely override the strict types manually to prevent conversion crashes

        existingFarmer.setDob(LocalDate.parse(updateDto.getDob()));

        existingFarmer.setGender(Farmer.Gender.valueOf(updateDto.getGender().toUpperCase()));

        Farmer updatedFarmer = farmerRepository.save(existingFarmer);

        log.info("Successfully updated profile for farmer ID: {}", updatedFarmer.getFarmerId());

        return mapToDto(updatedFarmer);

    }

    @Override

    public List<FarmerDTO> getAllFarmers() {

        log.debug("Fetching all farmers from the database");

        return farmerRepository.findAll().stream()

                .map(this::mapToDto)

                .collect(Collectors.toList());

    }


    private FarmerDTO mapToDto(Farmer farmer) {



        return modelMapper.map(farmer, FarmerDTO.class);

    }

}
