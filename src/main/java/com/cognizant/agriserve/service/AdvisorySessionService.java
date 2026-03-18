package com.cognizant.agriserve.service;

import com.cognizant.agriserve.dto.AdvisorySessionRequestDTO;
import com.cognizant.agriserve.entity.AdvisorySession;
import java.util.List;
import java.util.Map;

public interface AdvisorySessionService {
    // Links the DTO from the app with the secure OfficerID from the token
    AdvisorySession logAdvisorySession(AdvisorySessionRequestDTO dto, Integer officerId);
    List<AdvisorySession> getFarmerHistory(Integer farmerId);
    List<Map<String, Object>> getUsageAnalytics();
}