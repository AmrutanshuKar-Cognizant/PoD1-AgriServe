package com.cognizant.agriserve.service;

import com.cognizant.agriserve.dto.request.AuthRequestDTO;
import com.cognizant.agriserve.dto.response.AuthResponseDTO;
import com.cognizant.agriserve.dto.request.FarmerRegistrationRequestDTO;
import jakarta.validation.Valid;

public interface AuthService {


    String registerFarmer(FarmerRegistrationRequestDTO dto);

    AuthResponseDTO login(com.cognizant.agriserve.dto.request.@Valid AuthRequestDTO dto);

}