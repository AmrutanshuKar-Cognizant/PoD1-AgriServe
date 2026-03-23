package com.cognizant.agriserve.filter;

import com.cognizant.agriserve.service.impl.CustomUserDetailsService;
import com.cognizant.agriserve.util.AuthUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final AuthUtil authUtil;
    private final CustomUserDetailsService customUserDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // 1. Get the Authorization Header from the request
        String header = request.getHeader("Authorization");

        // 2. EARLY EXIT: If there is no header or it doesn't start with "Bearer ",
        // move on immediately. Spring Security will block them automatically.
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extract the token by removing "Bearer "
        String token = header.substring(7);

        // 3. THE CRITICAL FIX: Wrap the ENTIRE extraction and validation process in a try-catch.
        // If the token is expired, validateToken() or extractEmail() will throw an exception.
        try {
            String email = authUtil.extractEmail(token);

            // 4. If we have an email and the user is not already authenticated in this session
            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                // Load user details from your database
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

                // 5. Validate the token
                if (authUtil.validateToken(token)) {

                    // Create an Authentication Object for Spring Security
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // Set the Authentication in the Security Context
                    // This "logs in" the user for the duration of this single request
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (Exception e) {
            // Token is either expired, tampered with, or fundamentally broken.
            // We log it quietly here so Tomcat doesn't crash and return a nasty 500 error page.
            log.error("JWT Authentication failed: " + e.getMessage());
        }

        // 6. Continue the filter chain
        filterChain.doFilter(request, response);
    }
}