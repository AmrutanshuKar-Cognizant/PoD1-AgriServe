package com.cognizant.agriserve.service;

import com.cognizant.agriserve.dto.AuthRequestDTO;
import com.cognizant.agriserve.dto.AuthResponseDTO;
import com.cognizant.agriserve.dto.FarmerRegistrationRequestDTO;

public interface AuthService {


    String registerFarmer(FarmerRegistrationRequestDTO dto);

    AuthResponseDTO login(AuthRequestDTO dto);

}