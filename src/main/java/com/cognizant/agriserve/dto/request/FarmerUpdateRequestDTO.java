package com.cognizant.agriserve.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class FarmerUpdateRequestDTO {

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @NotBlank(message = "Date of birth is required")
    private String dob;

    @NotBlank(message = "Gender is required")
    private String gender;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "Contact info is required")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Invalid phone number format")
    private String contactInfo;

    @NotNull(message = "Land size is required")
    @PositiveOrZero(message = "Land size cannot be negative")
    private Double landSize;

    @NotBlank(message = "Crop type is required")
    private String cropType;
}