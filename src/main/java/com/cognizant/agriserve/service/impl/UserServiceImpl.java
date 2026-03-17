package com.cognizant.agriserve.service;

import com.cognizant.agriserve.dao.UserRepository;
import com.cognizant.agriserve.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Register a new user
    public User registerUser(User user) {

        // Check for duplicate email
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // Check for duplicate phone
        if (userRepository.existsByPhone(user.getPhone())) {
            throw new RuntimeException("Phone number already exists");
        }

        // Set default status
        user.setStatus("ACTIVE");

        return userRepository.save(user);
    }

    // Get user by ID
    public User getUserById(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // Get user by email (used for login)
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get users by role
    public List<User> getUsersByRole(User.Role role) {
        return userRepository.findByRole(role);
    }

    // Get users by status
    public List<User> getUsersByStatus(String status) {
        return userRepository.findByStatus(status);
    }

    // Update user details
    public User updateUser(Integer userId, User updatedUser) {

        User existingUser = getUserById(userId);

        existingUser.setName(updatedUser.getName());
        existingUser.setPhone(updatedUser.getPhone());
        existingUser.setStatus(updatedUser.getStatus());
        existingUser.setRole(updatedUser.getRole());

        return userRepository.save(existingUser);
    }

    // Deactivate user (soft delete)
    public void deactivateUser(Integer userId) {
        User user = getUserById(userId);
        user.setStatus("INACTIVE");
        userRepository.save(user);
    }

    // Delete user (hard delete - admin only)
    public void deleteUser(Integer userId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(userId);
    }
}