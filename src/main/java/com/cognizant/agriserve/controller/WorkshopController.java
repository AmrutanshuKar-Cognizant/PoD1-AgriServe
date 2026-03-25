package com.cognizant.agriserve.controller;

import com.cognizant.agriserve.dto.WorkshopDTO;
import com.cognizant.agriserve.exception.UnauthorizedActionException;
import com.cognizant.agriserve.service.WorkshopService;
import com.cognizant.agriserve.dao.UserRepository;
import com.cognizant.agriserve.entity.User;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/workshops")
public class WorkshopController {

    private final WorkshopService workshopService;
    private final UserRepository userRepository;

    public WorkshopController(WorkshopService workshopService, UserRepository userRepository) {
        this.workshopService = workshopService;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<WorkshopDTO> scheduleWorkshop(@Valid @RequestBody WorkshopDTO workshopDto) {
        log.info("Checking permissions for Workshop scheduling...");


        User requester = userRepository.findById(workshopDto.getOfficerId())
                .orElseThrow(() -> new UnauthorizedActionException("User not found to verify permissions"));

        if (requester.getRole() != User.Role.ProgramManager && requester.getRole() != User.Role.Admin) {
            log.error("Unauthorized Attempt: User {} is not a Program Manager", requester.getUserId());
            throw new UnauthorizedActionException("Only Program Managers are authorized to schedule workshops.");
        }

        log.info("Scheduling a new workshop for Program: {}", workshopDto.getProgramTitle());
        WorkshopDTO scheduledWorkshop = workshopService.scheduleWorkshop(workshopDto);
        return new ResponseEntity<>(scheduledWorkshop, HttpStatus.CREATED);
    }
//farmer access
    @GetMapping("/active")
    public ResponseEntity<List<WorkshopDTO>> getActiveWorkshops() {
        log.info("Fetching all active workshops for farmer discovery view");
        return ResponseEntity.ok(workshopService.getActiveWorkshopsForFarmers());
    }

    @GetMapping("/officer/{officerId}")
    public ResponseEntity<List<WorkshopDTO>> getWorkshopsByOfficer(@PathVariable Long officerId) {
        log.info("Fetching schedule for Extension Officer ID: {}", officerId);
        return ResponseEntity.ok(workshopService.getWorkshopsByOfficer(officerId));
    }

    @PatchMapping("/{workshopId}/status")
    public ResponseEntity<WorkshopDTO> updateWorkshopStatus(
            @PathVariable Long workshopId,
            @RequestParam String status) {
        log.info("Request to update Workshop ID: {} to status: {}", workshopId, status);
        return ResponseEntity.ok(workshopService.updateWorkshopStatus(workshopId, status));
    }

    @PutMapping("/{workshopId}")
    public ResponseEntity<WorkshopDTO> updateWorkshop(
            @PathVariable Long workshopId,
            @Valid @RequestBody WorkshopDTO workshopDto) {
        log.info("Request to edit details for Workshop ID: {}", workshopId);
        return ResponseEntity.ok(workshopService.updateWorkshop(workshopId, workshopDto));
    }

    @DeleteMapping("/{workshopId}")
    public ResponseEntity<Void> deleteWorkshop(@PathVariable Long workshopId) {
        log.info("Request to delete Workshop ID: {}", workshopId);
        workshopService.deleteWorkshop(workshopId);
        return ResponseEntity.noContent().build();
    }
}