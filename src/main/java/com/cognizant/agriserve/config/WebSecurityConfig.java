package com.cognizant.agriserve.config;

import com.cognizant.agriserve.filter.JwtAuthFilter;
import org.apache.tomcat.util.http.Method;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final HandlerExceptionResolver resolver;
    public WebSecurityConfig(JwtAuthFilter jwtAuthFilter,@Qualifier("handlerExceptionResolver")HandlerExceptionResolver resolver){
        this.jwtAuthFilter=jwtAuthFilter;
        this.resolver=resolver;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. Disable CSRF since we are using JWTs (Stateless APIs don't need CSRF protection)
                .csrf(csrf -> csrf.disable())

                // 2. Set session management to STATELESS (Spring won't create a web session)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            resolver.resolveException(request, response, null, accessDeniedException);
                        })
                )
                // 3. Configure API Endpoint Access Rules
                .authorizeHttpRequests(auth -> auth

                        // Public Endpoints (No token required)
                        .requestMatchers("/api/auth/**", "/").permitAll()

                        // Role-Restricted Endpoints
                        .requestMatchers("/api/advisory-content/upload").hasAnyRole("Admin", "ProgramManager")
                        .requestMatchers("/api/advisory-content/delete/**").hasAnyRole("Admin", "ProgramManager")
                        .requestMatchers("/api/advisory-content/active").hasAnyRole("Admin", "ProgramManager", "ExtensionOfficer", "Farmer")
                        .requestMatchers("/api/advisory-sessions/log").hasRole("ExtensionOfficer")
                        .requestMatchers("/api/advisory-sessions/history/**").hasAnyRole("ExtensionOfficer", "Farmer", "Admin")
                        .requestMatchers("/api/advisory-sessions/reports/**").hasAnyRole("Admin", "ProgramManager")
                        .requestMatchers("/api/compliance-records/**").hasRole("ComplianceOfficer")
                        .requestMatchers("/api/audits/**").hasRole("ComplianceOfficer")
                        .requestMatchers("/api/admin/documents/**").hasRole("Admin")
                        .requestMatchers("/api/farmers/profile").hasRole("Farmer")
                        .requestMatchers("/api/farmers/all").hasAnyRole("Admin", "ExtensionOfficer")
                        .requestMatchers("/api/farmers/documents").hasRole("Farmer")
                        .requestMatchers("/api/participations/register").hasRole("Farmer")
                        .requestMatchers("/api/participations/workshop/{workshopId}",
                                "/api/participations/farmer/{farmerId}").hasAnyRole("ExtensionOfficer", "ProgramManager", "Admin")
                        .requestMatchers("/api/participations/attendance").hasRole("ExtensionOfficer")
                        .requestMatchers("/api/programs/**").hasRole("ProgramManager")
                        .requestMatchers("/api/users/**").hasRole("Admin")
                        .requestMatchers("/api/workshops/**").hasRole("ProgramManager")


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