package com.cognizant.agriserve.controller;

import com.cognizant.agriserve.dto.AdvisorySessionRequestDTO;
import com.cognizant.agriserve.entity.AdvisorySession;
import com.cognizant.agriserve.service.AdvisorySessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/advisory-sessions")
@RequiredArgsConstructor
public class AdvisorySessionController {
    private final AdvisorySessionService sessionService;

    @PostMapping("/log")
    public ResponseEntity<AdvisorySession> logSession(
            @RequestBody AdvisorySessionRequestDTO dto,
            @RequestHeader("Officer-ID") Long officerId) {
        return ResponseEntity.ok(sessionService.logAdvisorySession(dto, officerId));
    }

    @GetMapping("/history/{farmerId}")
    public ResponseEntity<List<AdvisorySession>> getHistory(@PathVariable Long farmerId) {
        return ResponseEntity.ok(sessionService.getFarmerHistory(farmerId));
    }

    @GetMapping("/reports/usage")
    public ResponseEntity<List<Map<String, Object>>> getUsageReport() {
        return ResponseEntity.ok(sessionService.getUsageAnalytics());
    }
}