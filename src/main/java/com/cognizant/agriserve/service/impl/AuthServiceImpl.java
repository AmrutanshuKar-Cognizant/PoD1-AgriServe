package com.cognizant.agriserve.service.impl;

import com.cognizant.agriserve.dao.FarmerRepository;
import com.cognizant.agriserve.dao.UserRepository;
import com.cognizant.agriserve.dto.AuthRequestDTO;
import com.cognizant.agriserve.dto.AuthResponseDTO;
import com.cognizant.agriserve.dto.FarmerRegistrationRequestDTO;
import com.cognizant.agriserve.entity.Farmer;
import com.cognizant.agriserve.entity.User;
import com.cognizant.agriserve.service.AuthService;
import com.cognizant.agriserve.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final FarmerRepository farmerRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthUtil authUtil;
    private final AuthenticationManager authenticationManager; // Added this!

    @Override
    @Transactional
    public String registerFarmer(FarmerRegistrationRequestDTO dto) {
        log.info("Attempting to register new farmer with email: {}", dto.getEmail());

        // 1. Prevent SQL crashes by checking for duplicate emails first
        if (userRepository.existsByEmail(dto.getEmail())) {
            log.warn("Registration failed: Email {} is already taken.", dto.getEmail());
            throw new RuntimeException("Email is already in use!");
        }

        // 2. Create User Entry (The Login Credentials)
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setPhone(dto.getContactInfo()); // Mapped from contactInfo
        user.setRole(User.Role.Farmer); //
        user.setStatus("Active");

        User savedUser = userRepository.save(user);

        // 3. Create Farmer Profile Entry
        Farmer farmer = new Farmer();
        farmer.setName(dto.getName());

        // CONVERSION 1: String to LocalDate
        farmer.setDob(java.time.LocalDate.parse(dto.getDob()));

        // CONVERSION 2: String to Enum (converts "male" to MALE)
        farmer.setGender(Farmer.Gender.valueOf(dto.getGender().toUpperCase()));

        farmer.setAddress(dto.getAddress());
        farmer.setContactInfo(dto.getContactInfo());
        farmer.setLandSize(dto.getLandSize());
        farmer.setCropType(dto.getCropType());

        // CONVERSION 3: Use the Enum instead of a String
        farmer.setStatus(Farmer.Status.ACTIVE);

        farmer.setUser(savedUser);

        farmerRepository.save(farmer);

        log.info("Successfully registered farmer profile for email: {}", dto.getEmail());
        return "Farmer Registered Successfully!";
    }

    @Override
    public AuthResponseDTO login(AuthRequestDTO dto) {
        log.info("Attempting login for email: {}", dto.getEmail());

        try {
            // 1. Let Spring Security verify the password via your CustomUserDetailsService
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
            );
        } catch (Exception e) {
            log.warn("Failed login attempt for email: {}", dto.getEmail());
            throw new BadCredentialsException("Invalid email or password");
        }

        // 2. If we reach here, the password was correct! Fetch the user to get their role.
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found after authentication"));

        // 3. Generate the JWT Token
        String token = authUtil.generateToken(user.getEmail(), user.getRole().name());

        log.info("Successful login for email: {}. Issued JWT.", user.getEmail());

        // 4. Return the response to the Controller
        return new AuthResponseDTO(token, user.getEmail(), user.getRole().name());
    }
}