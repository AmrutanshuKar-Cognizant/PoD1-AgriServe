package com.cognizant.agriserve.controller;

import com.cognizant.agriserve.dto.AttendanceUpdateRequestDto;
import com.cognizant.agriserve.dto.ParticipationDto;
import com.cognizant.agriserve.service.ParticipationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/participations")
public class ParticipationController {

    private final ParticipationService participationService;

    public ParticipationController(ParticipationService participationService) {
        this.participationService = participationService;
    }

    // --- ENDPOINT 1: View Attendance Roster ---
    // Notice the {workshopId} in the URL path. This makes the URL dynamic!
    @GetMapping("/workshop/{workshopId}")
    public ResponseEntity<List<ParticipationDto>> getParticipantsForWorkshop(@PathVariable Long workshopId) {

        List<ParticipationDto> participants = participationService.getParticipantsForWorkshop(workshopId);
        return ResponseEntity.ok(participants);
    }

    // --- ENDPOINT 2: Update Attendance Status ---
    @PutMapping("/attendance")
    public ResponseEntity<ParticipationDto> updateAttendance(@RequestBody AttendanceUpdateRequestDto requestDto) {

        ParticipationDto updatedRecord = participationService.updateAttendance(requestDto);
        return ResponseEntity.ok(updatedRecord);
    }
}