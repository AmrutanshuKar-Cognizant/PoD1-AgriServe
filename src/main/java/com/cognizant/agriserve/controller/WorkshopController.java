package com.cognizant.agriserve.controller;

import com.cognizant.agriserve.dto.WorkshopDto;
import com.cognizant.agriserve.service.WorkshopService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workshops")
public class WorkshopController {

    private final WorkshopService workshopService;

    public WorkshopController(WorkshopService workshopService) {
        this.workshopService = workshopService;
    }

    // --- ENDPOINT 1: Schedule a New Workshop (Program Manager) ---
    @PostMapping
    public ResponseEntity<WorkshopDto> scheduleWorkshop(@RequestBody WorkshopDto workshopDto) {
        WorkshopDto scheduledWorkshop = workshopService.scheduleWorkshop(workshopDto);
        return new ResponseEntity<>(scheduledWorkshop, HttpStatus.CREATED);
    }

    // --- ENDPOINT 2: Farmer Discovery View (Active Workshops) ---
    @GetMapping("/active")
    public ResponseEntity<List<WorkshopDto>> getActiveWorkshops() {
        return ResponseEntity.ok(workshopService.getActiveWorkshopsForFarmers());
    }

    // --- ENDPOINT 3: Extension Officer Schedule View ---
    // The URL will look like: /api/workshops/officer/101
    @GetMapping("/officer/{officerId}")
    public ResponseEntity<List<WorkshopDto>> getWorkshopsByOfficer(@PathVariable Long officerId) {
        return ResponseEntity.ok(workshopService.getWorkshopsByOfficer(officerId));
    }

    // --- ENDPOINT 4: Update Workshop Status (EXTO-005) ---
    // The URL will look like: /api/workshops/5/status?status=Ongoing
    @PatchMapping("/{workshopId}/status")
    public ResponseEntity<WorkshopDto> updateWorkshopStatus(
            @PathVariable Long workshopId,
            @RequestParam String status) {

        return ResponseEntity.ok(workshopService.updateWorkshopStatus(workshopId, status));
    }
}