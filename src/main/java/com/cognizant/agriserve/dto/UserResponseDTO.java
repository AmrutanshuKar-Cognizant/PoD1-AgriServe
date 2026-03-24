package com.cognizant.agriserve.dto;

import com.cognizant.agriserve.entity.User;
import lombok.Data;

@Data

public class UserResponseDTO {

    private Integer userId; // Or Long, depending on your Entity
    private String name;
    private User.Role role;
    private String email;
    private String phone;
    private String status;

}
