package com.cognizant.agriserve.service;

import com.cognizant.agriserve.dto.AuthRequestDTO;
import com.cognizant.agriserve.dto.AuthResponseDTO;
import com.cognizant.agriserve.dto.FarmerRegistrationRequestDto;

public interface AuthService {


    String registerFarmer(FarmerRegistrationRequestDto dto);

    AuthResponseDTO login(AuthRequestDTO dto);

}