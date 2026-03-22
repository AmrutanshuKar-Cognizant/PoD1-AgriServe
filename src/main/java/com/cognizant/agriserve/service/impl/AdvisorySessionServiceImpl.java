package com.cognizant.agriserve.service.impl;

import com.cognizant.agriserve.dao.AdvisorySessionRepository;
import com.cognizant.agriserve.dao.AdvisoryContentRepository;
import com.cognizant.agriserve.dao.FarmerRepository;
import com.cognizant.agriserve.dao.UserRepository;
import com.cognizant.agriserve.dto.AdvisorySessionRequestDTO;
import com.cognizant.agriserve.entity.AdvisoryContent;
import com.cognizant.agriserve.entity.AdvisorySession;
import com.cognizant.agriserve.entity.Farmer;
import com.cognizant.agriserve.entity.User;
import com.cognizant.agriserve.service.AdvisorySessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
@RequiredArgsConstructor
@Service
public class AdvisorySessionServiceImpl implements AdvisorySessionService {

    private final AdvisorySessionRepository sessionRepo;
    private final FarmerRepository farmerRepo;
    private final AdvisoryContentRepository contentRepo;
    private final UserRepository userRepo;

    @Override
    public AdvisorySession logAdvisorySession(AdvisorySessionRequestDTO dto, Long officerId) {
        // 1. Create a new database Entity
        AdvisorySession session = new AdvisorySession();

        // 2. Fetch the Farmer Object (The person getting advice)
        // We use .longValue() if your Farmer ID in the Farmer table is a Long
        Farmer farmer = farmerRepo.findById(dto.getFarmerId())
                .orElseThrow(() -> new RuntimeException("Farmer not found with ID: " + dto.getFarmerId()));
        session.setFarmer(farmer);

        // 3. Fetch the Officer/User Object (The person giving advice)
        User officer = userRepo.findById(officerId)
                .orElseThrow(() -> new RuntimeException("Officer not found with ID: " + officerId));
        session.setOfficer(officer);

        // 4. Fetch the AdvisoryContent Object (The guide used)
        AdvisoryContent content = contentRepo.findById(dto.getContentId())
                .orElseThrow(() -> new RuntimeException("Content not found with ID: " + dto.getContentId()));
        session.setContent(content);

        // 5. Set remaining fields and notes
        session.setFeedback(dto.getFeedback());
        session.setDate(LocalDateTime.now());
        session.setStatus("Completed");

        // 6. Save the session with full relationships (Foreign Keys)
        return sessionRepo.save(session);
    }

    @Override
    public List<AdvisorySession> getFarmerHistory(Long farmerId) {
        return sessionRepo.findByFarmer_FarmerId(farmerId);
    }

    @Override
    public List<Map<String, Object>> getUsageAnalytics() {
        return sessionRepo.getContentUsageReport();
    }
}