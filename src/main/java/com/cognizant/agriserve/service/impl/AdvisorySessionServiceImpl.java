package com.cognizant.agriserve.service.impl;

import com.cognizant.agriserve.dao.AdvisorySessionRepository;
import com.cognizant.agriserve.dao.AdvisoryContentRepository;
import com.cognizant.agriserve.dto.AdvisorySessionRequestDTO;
import com.cognizant.agriserve.entity.AdvisoryContent;
import com.cognizant.agriserve.entity.AdvisorySession;
import com.cognizant.agriserve.service.AdvisorySessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
@RequiredArgsConstructor
@Service
public class AdvisorySessionServiceImpl implements AdvisorySessionService {

//    @Autowired
    private final AdvisorySessionRepository sessionRepo;

//    @Autowired
    private final AdvisoryContentRepository contentRepo;

    @Override
    public AdvisorySession logAdvisorySession(AdvisorySessionRequestDTO dto, Integer officerId) {
        // 1. Unpacking the DTO: Create a new database Entity
        AdvisorySession session = new AdvisorySession();

        // 2. Setting IDs and Notes provided by the Officer
        session.setFarmerId(dto.getFarmerId());
        session.setFeedback(dto.getFeedback());

        // 3. Linking the Guide: Find the AdvisoryContent by the ID provided in DTO
        AdvisoryContent content = contentRepo.findById(dto.getContentId())
                .orElseThrow(() -> new RuntimeException("Content not found with ID: " + dto.getContentId()));
        session.setContent(content);

        // 4. Secure System Fields: These are NOT in the DTO for security reasons
        session.setOfficerId(officerId); // Stamped from JWT
        session.setDate(LocalDateTime.now()); // Stamped by Server Time
        session.setStatus("Completed");

        // 5. Database Save: Hand the finished Entity to the Repository
        return sessionRepo.save(session);
    }

    @Override
    public List<AdvisorySession> getFarmerHistory(Integer farmerId) {
        return sessionRepo.findByFarmerId(farmerId);
    }

    @Override
    public List<Map<String, Object>> getUsageAnalytics() {
        return sessionRepo.getContentUsageReport();
    }
}