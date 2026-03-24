package com.cognizant.agriserve.config;

import com.cognizant.agriserve.filter.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. Disable CSRF since we are using JWTs (Stateless APIs don't need CSRF protection)
                .csrf(csrf -> csrf.disable())

                // 2. Set session management to STATELESS (Spring won't create a web session)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // 3. Configure API Endpoint Access Rules
                .authorizeHttpRequests(auth -> auth

                        // Public Endpoints (No token required)
                        .requestMatchers("/api/auth/**").permitAll()

                        // Role-Restricted Endpoints
                        .requestMatchers("/api/advisory-content/upload").hasAnyRole("ProgramManager", "Admin")
                        .requestMatchers("/api/advisory-sessions/log").hasRole("ExtensionOfficer")
                        .requestMatchers("/api/compliance-records").hasRole("ComplianceOfficer")


                        // Default Rule: Everything else requires a valid JWT token
                        .anyRequest().authenticated()
                )

                // 4. Inject our custom JWT Filter before Spring's default username/password filter
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}