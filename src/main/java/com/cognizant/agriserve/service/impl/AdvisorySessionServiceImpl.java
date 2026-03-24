package com.cognizant.agriserve.service.impl;

import com.cognizant.agriserve.dao.*;
import com.cognizant.agriserve.dto.*;
import com.cognizant.agriserve.entity.*;
import com.cognizant.agriserve.service.AdvisorySessionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
    public AdvisorySessionResponseDTO logAdvisorySession(AdvisorySessionRequestDTO dto, Long officerId) {
        AdvisorySession session = new AdvisorySession();

        // Replace your current .orElseThrow() lines with this:
        Farmer farmer = farmerRepo.findById(dto.getFarmerId())
                .orElseThrow(() -> new ResourceNotFoundException("Farmer not found with ID: " + dto.getFarmerId()));

        User officer = userRepo.findById(officerId)
                .orElseThrow(() -> new ResourceNotFoundException("Officer not found with ID: " + officerId));

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

    @Override
    public List<Map<String, Object>> getUsageAnalytics() {
        return sessionRepo.getContentUsageReport();
    }
}