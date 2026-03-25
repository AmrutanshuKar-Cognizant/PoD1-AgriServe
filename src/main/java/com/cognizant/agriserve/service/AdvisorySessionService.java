package com.cognizant.agriserve.service;

import com.cognizant.agriserve.dto.request.AdvisorySessionRequestDTO;
import com.cognizant.agriserve.dto.response.AdvisorySessionResponseDTO;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Map;

public interface AdvisorySessionService {

    // Accepts RequestDTO and returns ResponseDTO
    AdvisorySessionResponseDTO logAdvisorySession(AdvisorySessionRequestDTO dto, Authentication authentication);

    // Returns a history list of ResponseDTOs for a specific farmer
    List<AdvisorySessionResponseDTO> getFarmerHistory(Long farmerId);

    // Returns a map for the Manager's dashboard analytics
    List<Map<String, Object>> getUsageAnalytics();
}