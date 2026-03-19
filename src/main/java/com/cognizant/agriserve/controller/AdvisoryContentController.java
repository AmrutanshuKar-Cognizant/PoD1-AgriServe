package com.cognizant.agriserve.controller;

import com.cognizant.agriserve.entity.AdvisoryContent;
import com.cognizant.agriserve.service.AdvisoryContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/advisory-content")
@RequiredArgsConstructor
public class AdvisoryContentController {
    private final AdvisoryContentService contentService;

    @PostMapping("/upload")
    public ResponseEntity<AdvisoryContent> uploadContent(@RequestBody AdvisoryContent content) {
        return ResponseEntity.ok(contentService.saveContent(content));
    }

    @GetMapping("/active")
    public ResponseEntity<List<AdvisoryContent>> getActiveContent() {
        return ResponseEntity.ok(contentService.getAllActiveContent());
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<String> removeContent(@PathVariable Integer id) {
        contentService.softDeleteContent(id);
        return ResponseEntity.ok("Content with ID " + id + " has been marked as Inactive.");
    }
}