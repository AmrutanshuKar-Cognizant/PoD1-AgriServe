package com.cognizant.agriserve.service.impl;

import com.cognizant.agriserve.dao.*;
import com.cognizant.agriserve.dto.*;
import com.cognizant.agriserve.entity.*;
import com.cognizant.agriserve.exception.ResourceNotFoundException;
import com.cognizant.agriserve.service.AdvisorySessionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdvisorySessionServiceImpl implements AdvisorySessionService {

    private final AdvisorySessionRepository sessionRepo;
    private final FarmerRepository farmerRepo;
    private final AdvisoryContentRepository contentRepo;
    private final UserRepository userRepo;
    private final ModelMapper modelMapper;

    @Override
    public AdvisorySessionResponseDTO logAdvisorySession(AdvisorySessionRequestDTO dto,Authentication authentication) {
        AdvisorySession session = new AdvisorySession();

        // Replace your current .orElseThrow() lines with this:
        Farmer farmer = farmerRepo.findById(dto.getFarmerId())
                .orElseThrow(() -> new ResourceNotFoundException("Farmer not found with ID: " + dto.getFarmerId()));
        User officer=curuser(authentication);

        if (officer.getRole() != User.Role.ExtensionOfficer) {
            // This is better than RuntimeException because it triggers a 403 Forbidden
            throw new RuntimeException("Only Extension Officers can log sessions.");
        }
        AdvisoryContent content = contentRepo.findById(dto.getContentId())
                .orElseThrow(() -> new ResourceNotFoundException("Advisory Content not found with ID: " + dto.getContentId()));

        session.setFarmer(farmer);
        session.setOfficer(officer);
        session.setContent(content);
        session.setFeedback(dto.getFeedback());
        session.setDate(LocalDateTime.now());
        session.setStatus("Completed");

        AdvisorySession saved = sessionRepo.save(session);

        AdvisorySessionResponseDTO response = modelMapper.map(saved, AdvisorySessionResponseDTO.class);
        response.setFarmerName(farmer.getName());
        response.setOfficerName(officer.getName());
        response.setContentTitle(content.getTitle());

        return response;
    }

    @Override
    public List<AdvisorySessionResponseDTO> getFarmerHistory(Long farmerId) {
        return sessionRepo.findByFarmer_FarmerId(farmerId).stream()
                .map(s -> {
                    AdvisorySessionResponseDTO res = modelMapper.map(s, AdvisorySessionResponseDTO.class);
                    res.setFarmerName(s.getFarmer().getName());
                    res.setOfficerName(s.getOfficer().getName());
                    res.setContentTitle(s.getContent().getTitle());
                    return res;
                }).collect(Collectors.toList());
    }
    private User curuser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            // This will be caught and return a 401 Unauthorized
            throw new RuntimeException("User must be logged in to perform this action.");
        }
        String userEmail = authentication.getName();
        return userRepo.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Logged-in user not found for email: " + userEmail));
    }
    @Override
    public List<Map<String, Object>> getUsageAnalytics() {
        return sessionRepo.getContentUsageReport();
    }
}