package com.cognizant.agriserve.service;

import com.cognizant.agriserve.dto.UserRequestDTO;
import com.cognizant.agriserve.dto.UserResponseDTO;
import com.cognizant.agriserve.entity.User;

import java.util.List;

public interface UserService {

    UserResponseDTO getUserById(Integer userId);
    UserResponseDTO getUserByEmail(String email);
    List<UserResponseDTO> getAllUsers();
    List<UserResponseDTO> getUsersByRole(User.Role role);
    List<UserResponseDTO> getUsersByStatus(String status);
    // Uses the validated Request DTO to prevent malicious updates
    UserResponseDTO updateUser(Integer userId, UserRequestDTO updatedUserDTO);
    void deactivateUser(Integer userId);
    void deleteUser(Integer userId);

}
