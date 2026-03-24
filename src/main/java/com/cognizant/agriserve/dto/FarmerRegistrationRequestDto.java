package com.cognizant.agriserve.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class FarmerRegistrationRequestDto {

    // CREDENTIALS FOR THE USER TABLE
    @NotBlank(message = "Email is required")
    @Email(message = "Must be a valid email format")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    // PROFILE INFO FOR THE FARMER TABLE
    @NotBlank(message="Name is required")
    private String name;

    @NotBlank(message="DOB is required")
    private String dob;

    @NotBlank(message = "Gender is required")
    private String gender;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "Contact info is required")
    private String contactInfo;

    @NotNull(message = "Land size is required")
    @Positive(message = "Land size must be greater than 0")
    private Double landSize;

    @NotBlank(message = "Crop type is required")
    private String cropType;
}