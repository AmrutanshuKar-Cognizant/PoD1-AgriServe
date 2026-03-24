package com.cognizant.agriserve.config;

import com.cognizant.agriserve.dao.UserRepository;
import com.cognizant.agriserve.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DatabaseSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        String officerEmail = "officer@agriserve.com";

        // Check if the user already exists to prevent duplicate key errors on restart
        if (!userRepository.existsByEmail(officerEmail)) {
            log.info("Seeding database: Creating default Compliance Officer...");

            User officer = new User();
            officer.setName("Alice Compliance");
            officer.setEmail(officerEmail);

            // Starts with 9 and is 10 digits to perfectly pass your @Pattern validation
            officer.setPhone("9876543210");

            // Hashes the password for Spring Security
            officer.setPassword(passwordEncoder.encode("password123"));

            // Accesses your nested Enum exactly as you defined it
            officer.setRole(User.Role.ComplianceOfficer);

            // Fills the @NotBlank requirement for the status field
            officer.setStatus("ACTIVE");

            userRepository.save(officer);

            log.info("Default Compliance Officer created successfully!");
        } else {
            log.info("Database seeding skipped: Compliance Officer already exists.");
        }
    }
}