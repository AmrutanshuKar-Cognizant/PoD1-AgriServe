package com.cognizant.agriserve.controller;

import com.cognizant.agriserve.dto.AdvisoryContentRequestDTO;
import com.cognizant.agriserve.dto.AdvisoryContentResponseDTO;
import com.cognizant.agriserve.entity.AdvisoryContent;
import com.cognizant.agriserve.service.AdvisoryContentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/advisory-content")
@RequiredArgsConstructor
@Validated
public class AdvisoryContentController {
    private final AdvisoryContentService contentService;

    @PostMapping("/upload")
    public ResponseEntity<AdvisoryContentResponseDTO> uploadContent(
            @Valid @RequestBody AdvisoryContentRequestDTO requestDto) { // Changed to RequestDTO
        return ResponseEntity.ok(contentService.saveContent(requestDto));
    }

    @GetMapping("/active")
    public ResponseEntity<List<AdvisoryContentResponseDTO>> getActiveContent() {
        return ResponseEntity.ok(contentService.getAllActiveContent());
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<String> removeContent(@PathVariable @Min(value = 1, message = "ID must be positive") Long id) {
        contentService.softDeleteContent(id);
        return ResponseEntity.ok("Content marked as Inactive.");
    }
}