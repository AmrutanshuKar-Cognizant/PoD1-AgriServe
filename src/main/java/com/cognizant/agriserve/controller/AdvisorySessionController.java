package com.cognizant.agriserve.controller;

import com.cognizant.agriserve.dto.AdvisorySessionRequestDTO;
import com.cognizant.agriserve.dto.AdvisorySessionResponseDTO;
import com.cognizant.agriserve.service.AdvisorySessionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
            @RequestHeader("Officer-ID") @Min(1) Long officerId) {
        return ResponseEntity.ok(sessionService.logAdvisorySession(dto, officerId));
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