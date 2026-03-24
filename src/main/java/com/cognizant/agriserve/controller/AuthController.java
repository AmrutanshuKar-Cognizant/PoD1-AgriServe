package com.cognizant.agriserve.controller;

import com.cognizant.agriserve.dto.AuthRequestDTO;
import com.cognizant.agriserve.dto.AuthResponseDTO;
import com.cognizant.agriserve.dto.FarmerRegistrationRequestDto;
import com.cognizant.agriserve.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // REGISTER A NEW FARMER
    @PostMapping("/register/farmer")
    public ResponseEntity<String> register(@Valid @RequestBody FarmerRegistrationRequestDto request) {

        // Log the attempt (assuming your DTO has a getEmail() method)
        log.info("API Request: Registering new farmer with email: {}", request.getEmail());

        String response = authService.registerFarmer(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // LOGIN AND RECEIVE JWT
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody AuthRequestDTO request) {

        // Log the login attempt
        log.info("API Request: Login attempt for email: {}", request.getEmail());

        AuthResponseDTO response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}