package com.cognizant.agriserve.controller;

import com.cognizant.agriserve.dto.request.AttendanceUpdateRequestDTO;
import com.cognizant.agriserve.dto.ParticipationDTO;
import com.cognizant.agriserve.service.ParticipationService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/participations")
public class ParticipationController {

    private final ParticipationService participationService;

    public ParticipationController(ParticipationService participationService) {
        this.participationService = participationService;
    }

    @PostMapping("/register")
    public ResponseEntity<ParticipationDTO> registerForWorkshop(@Valid @RequestBody ParticipationDTO dto) {
        String loggedInEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("Registration request: Farmer Email {} for Workshop {}", loggedInEmail, dto.getWorkshopId());

        return new ResponseEntity<>(participationService.registerForWorkshop(dto, loggedInEmail), HttpStatus.CREATED);
    }

    @GetMapping("/workshop/{workshopId}")
    public ResponseEntity<List<ParticipationDTO>> getParticipantsForWorkshop(@PathVariable Long workshopId) {
        log.info("Fetching roster for Workshop ID: {}", workshopId);
        return ResponseEntity.ok(participationService.getParticipantsForWorkshop(workshopId));
    }

    @GetMapping("/farmer/{farmerId}")
    public ResponseEntity<List<ParticipationDTO>> getParticipationByFarmerId(@PathVariable Long farmerId) {
        log.info("Fetching history for Farmer ID: {}", farmerId);
        return ResponseEntity.ok(participationService.getParticipationByFarmerId(farmerId));
    }

    @PutMapping("/attendance")
    public ResponseEntity<ParticipationDTO> updateAttendance(@Valid @RequestBody AttendanceUpdateRequestDTO requestDto) {
        log.info("Updating attendance for Participation ID: {}", requestDto.getParticipationId());
        return ResponseEntity.ok(participationService.updateAttendance(requestDto));
    }
}