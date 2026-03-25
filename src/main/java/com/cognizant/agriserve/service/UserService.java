package com.cognizant.agriserve.service;

import com.cognizant.agriserve.dto.request.UserRequestDTO;
import com.cognizant.agriserve.dto.response.UserResponseDTO;
import com.cognizant.agriserve.entity.User;

import java.util.List;

public interface UserService {

    UserResponseDTO getUserById(Long userId);
    UserResponseDTO getUserByEmail(String email);
    List<UserResponseDTO> getAllUsers();
    List<UserResponseDTO> getUsersByRole(User.Role role);
    List<UserResponseDTO> getUsersByStatus(String status);
    // Uses the validated Request DTO to prevent malicious updates
    UserResponseDTO updateUser(Long userId, UserRequestDTO updatedUserDTO);
    void deactivateUser(Long userId);
    void deleteUser(Long userId);

}
