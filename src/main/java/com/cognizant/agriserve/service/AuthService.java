package com.cognizant.agriserve.service;

import com.cognizant.agriserve.dto.request.AuthRequestDTO;
import com.cognizant.agriserve.dto.response.AuthResponseDTO;
import com.cognizant.agriserve.dto.request.FarmerRegistrationRequestDto;

public interface AuthService {


    String registerFarmer(FarmerRegistrationRequestDto dto);

    AuthResponseDTO login(AuthRequestDTO dto);

}