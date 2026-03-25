package com.cognizant.agriserve.controller;

import com.cognizant.agriserve.dto.request.AdvisorySessionRequestDTO;
import com.cognizant.agriserve.dto.response.AdvisorySessionResponseDTO;
import com.cognizant.agriserve.service.AdvisorySessionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/advisory-sessions")
@RequiredArgsConstructor
@Validated
public class AdvisorySessionController {
    private final AdvisorySessionService sessionService;

    @PostMapping("/log")
    public ResponseEntity<AdvisorySessionResponseDTO> logSession(
            @Valid @RequestBody AdvisorySessionRequestDTO dto,
             Authentication authentication) {
        return ResponseEntity.ok(sessionService.logAdvisorySession(dto,authentication));
    }

    @GetMapping("/history/{farmerId}")
    public ResponseEntity<List<AdvisorySessionResponseDTO>> getHistory(@PathVariable @Min(1) Long farmerId) {
        return ResponseEntity.ok(sessionService.getFarmerHistory(farmerId));
    }

    @GetMapping("/reports/usage")
    public ResponseEntity<List<Map<String, Object>>> getUsageReport() {
        return ResponseEntity.ok(sessionService.getUsageAnalytics());
    }
}