package com.cognizant.agriserve.controller;

import com.cognizant.agriserve.dto.FarmerDTO;
import com.cognizant.agriserve.dto.FarmerUpdateRequestDTO;
import com.cognizant.agriserve.service.FarmerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

@Slf4j
@RestController

@RequestMapping("/api/farmers")  // base URL

@RequiredArgsConstructor

public class FarmerController {
    private final FarmerService farmerService;  // service injection

    // API 1: get profile
    @GetMapping("/profile")
    public ResponseEntity<FarmerDTO> getMyProfile(Principal principal) {

        // principal.getName() extracts the exact email inside the validated JWT token
        String email = principal.getName();

        log.info("API Request: Fetching profile for authenticated user: {}", email);

        // calls service layer
        FarmerDTO profile = farmerService.getFarmerProfile(email); // fetch farmer data
        return ResponseEntity.ok(profile);

    }


    // API 2: Updates the profile of the currently logged-in farmer.
    @PutMapping("/profile")
    public ResponseEntity<FarmerDTO> updateMyProfile(

            Principal principal,  // gets logged-in user

            @Valid @RequestBody FarmerUpdateRequestDTO updateDto) {  // JSON -> DTO, input validation

        String email = principal.getName();  // gets email

        log.info("API Request: Updating profile for authenticated user: {}", email);

        // calls service layer
        FarmerDTO updatedProfile = farmerService.updateFarmerProfile(email, updateDto); // updates data

        return ResponseEntity.ok(updatedProfile);

    }
//  API 3:  get all farmers ( restricted to Admin or Extension Officer roles)
    @GetMapping("/all")

    public ResponseEntity<List<FarmerDTO>> getAllFarmers() {

        log.info("API Request: Fetching all registered farmers");

        // calls service method
        List<FarmerDTO> farmers = farmerService.getAllFarmers();

        return ResponseEntity.ok(farmers);

    }

}
