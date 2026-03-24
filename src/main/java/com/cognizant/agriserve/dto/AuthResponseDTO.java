package com.cognizant.agriserve.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AuthResponseDTO {
    private String token;
    private String email;
    private String role;
}