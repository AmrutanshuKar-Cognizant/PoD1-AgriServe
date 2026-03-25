package com.cognizant.agriserve.config;

import com.cognizant.agriserve.dao.UserRepository;
import com.cognizant.agriserve.entity.User;
import com.cognizant.agriserve.entity.User.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class DatabaseSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Only seed if the user table is empty
        if (userRepository.count() == 0) {
            log.info("Starting Database Seeding for AgriServe Internal Roles...");

            try {
                // All staff will have this default password for testing
                String commonPassword = passwordEncoder.encode("Password123");

                // 1. Administrators (System Control)
                createUser("System Admin", "admin@agriserve.com", Role.Admin, "9998887770", commonPassword);

                // 2. Extension Officers (The ones who log sessions)
                createUser("Extension Officer", "karthik@agriserve.com", Role.ExtensionOfficer, "9876543210", commonPassword);

                // 3. Compliance Officers (Regulatory checks)
                createUser("Compliance Officer", "compliance@agriserve.com", Role.ComplianceOfficer, "9876543212", commonPassword);

                // 4. Auditors (Reviewing logs)
                createUser("Auditor Adyasha", "auditor@agriserve.com", Role.Auditor, "9876543213", commonPassword);

                // 5. Program Managers (Usage Analytics)
                createUser("Manager Aditi", "manager@agriserve.com", Role.ProgramManager, "9876543214", commonPassword);
                createUser("Manager harshith", "harshith@agriserve.com", Role.ProgramManager, "9573454208", commonPassword);

                log.info("Database Seeding Completed Successfully!");
                log.info("Internal Staff created. Farmers should be registered via the Auth Controller.");

            } catch (Exception e) {
                log.error("Seeding failed: {}", e.getMessage());
            }
        } else {
            log.info("Database already contains users. Skipping seeder.");
        }
    }

    private void createUser(String name, String email, Role role, String phone, String password) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setRole(role);
        user.setPhone(phone);
        user.setPassword(password);
        user.setStatus("Active");

        userRepository.save(user);
        log.debug("Created internal user: {} with role: {}", email, role);
    }
}