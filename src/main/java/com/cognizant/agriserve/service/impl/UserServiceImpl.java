package com.cognizant.agriserve.service.impl;

import com.cognizant.agriserve.dao.UserRepository;
import com.cognizant.agriserve.dto.request.UserRequestDTO;
import com.cognizant.agriserve.dto.response.UserResponseDTO;
import com.cognizant.agriserve.entity.User;
import com.cognizant.agriserve.exception.ResourceNotFoundException;
import com.cognizant.agriserve.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j

@Service

@RequiredArgsConstructor

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    @Override

    public UserResponseDTO getUserById(Long userId) {

        log.debug("Fetching user with ID: {}", userId);

        User user = userRepository.findById(userId)

                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        return mapToDTO(user);

    }

    @Override

    public UserResponseDTO getUserByEmail(String email) {

        log.debug("Fetching user with email: {}", email);

        User user = userRepository.findByEmail(email)

                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

        return mapToDTO(user);

    }

    @Override

    public List<UserResponseDTO> getAllUsers() {

        log.debug("Fetching all users");

        return userRepository.findAll().stream()

                .map(this::mapToDTO)

                .collect(Collectors.toList());

    }

    @Override

    public List<UserResponseDTO> getUsersByRole(User.Role role) {

        log.debug("Fetching users by role: {}", role);

        return userRepository.findByRole(role).stream()

                .map(this::mapToDTO)

                .collect(Collectors.toList());

    }

    @Override

    public List<UserResponseDTO> getUsersByStatus(String status) {

        log.debug("Fetching users by status: {}", status);

        return userRepository.findByStatus(status).stream()

                .map(this::mapToDTO)

                .collect(Collectors.toList());

    }

    @Override

    @Transactional

    public UserResponseDTO updateUser(Long userId, UserRequestDTO updatedUserDTO) {

        log.info("Updating user with ID: {}", userId);

        User existingUser = userRepository.findById(userId)

                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        // Safely update allowed fields only

        existingUser.setName(updatedUserDTO.getName());

        existingUser.setPhone(updatedUserDTO.getPhone());

        existingUser.setRole(updatedUserDTO.getRole());

        existingUser.setStatus(updatedUserDTO.getStatus());

        User savedUser = userRepository.save(existingUser);

        return mapToDTO(savedUser);

    }

    @Override

    @Transactional

    public void deactivateUser(Long userId) {

        log.info("Deactivating user with ID: {}", userId);

        User user = userRepository.findById(userId)

                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        user.setStatus("INACTIVE");

        userRepository.save(user);

    }

    @Override
    @Transactional

    public void deleteUser(Long userId) {

        log.warn("Hard deleting user with ID: {}", userId);
        if (!userRepository.existsById(userId)) {

            throw new ResourceNotFoundException("User not found with ID: " + userId);

        }
        userRepository.deleteById(userId);

    }


    private UserResponseDTO mapToDTO(User user) {
        return modelMapper.map(user, UserResponseDTO.class);

    }

}
