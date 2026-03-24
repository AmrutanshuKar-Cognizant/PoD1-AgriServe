package com.cognizant.agriserve.dto;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Data

public class FarmerDocumentUploadRequestDto {

    @NotBlank(message = "Document type is required (e.g., Aadhar, Land Permit)")

    private String docType;

    @NotBlank(message = "File URI is required")

    private String fileURI; // Assumes the frontend uploads the file to a cloud bucket (like AWS S3) and sends the link here

}
