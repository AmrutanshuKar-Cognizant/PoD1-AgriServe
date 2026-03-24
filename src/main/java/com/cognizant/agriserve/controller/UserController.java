package com.cognizant.agriserve.controller;

import com.cognizant.agriserve.dto.UserRequestDTO;
import com.cognizant.agriserve.dto.UserResponseDTO;
import com.cognizant.agriserve.entity.User;
import com.cognizant.agriserve.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor // Replaces @Autowired for cleaner constructor injection

public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")

    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        log.info("API Request: Fetching user with ID: {}", id);
        return ResponseEntity.ok(userService.getUserById(id));

    }

    @GetMapping

    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        log.info("API Request: Fetching all users");
        return ResponseEntity.ok(userService.getAllUsers());

    }

    @GetMapping("/role/{role}")

    public ResponseEntity<List<UserResponseDTO>> getUsersByRole(@PathVariable User.Role role) {
        log.info("API Request: Fetching users with role: {}", role);
        return ResponseEntity.ok(userService.getUsersByRole(role));

    }

    @GetMapping("/status/{status}")

    public ResponseEntity<List<UserResponseDTO>> getUsersByStatus(@PathVariable String status) {
        log.info("API Request: Fetching users with status: {}", status);
        return ResponseEntity.ok(userService.getUsersByStatus(status));

    }

    @PutMapping("/{id}")

    public ResponseEntity<UserResponseDTO> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserRequestDTO userRequestDTO) {

        log.info("API Request: Updating user ID: {}", id);
        return ResponseEntity.ok(userService.updateUser(id, userRequestDTO));

    }

    @PutMapping("/deactivate/{id}")

    public ResponseEntity<String> deactivateUser(@PathVariable Long id) {
        log.info("API Request: Deactivating user ID: {}", id);
        userService.deactivateUser(id);
        return ResponseEntity.ok("User deactivated successfully");

    }


    @DeleteMapping("/{id}")

    public ResponseEntity<String> deleteUser(@PathVariable Long id) {

        log.warn("API Request: Hard deleting user ID: {}", id);
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");

    }

}
