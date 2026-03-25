package com.cognizant.agriserve.controller;

import com.cognizant.agriserve.dto.TrainingProgramDTO;
import com.cognizant.agriserve.service.TrainingProgramService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/programs")
public class TrainingProgramController {

    private final TrainingProgramService programService;

    public TrainingProgramController(TrainingProgramService programService) {
        this.programService = programService;
    }


    @PostMapping
    public ResponseEntity<TrainingProgramDTO> createProgram(@Valid @RequestBody TrainingProgramDTO programDto) {
        log.info("Received request to create a new Training Program: {}", programDto.getTitle());
        TrainingProgramDTO savedProgram = programService.createProgram(programDto);
        return new ResponseEntity<>(savedProgram, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TrainingProgramDTO>> getAllPrograms() {
        log.info("Fetching all available Training Programs");
        return ResponseEntity.ok(programService.getAllPrograms());
    }

    @GetMapping("/{programId}")
    public ResponseEntity<TrainingProgramDTO> getProgramById(@PathVariable Long programId) {
        log.info("Fetching details for Training Program ID: {}", programId);
        return ResponseEntity.ok(programService.getProgramById(programId));
    }

    @PutMapping("/{programId}")
    public ResponseEntity<TrainingProgramDTO> updateProgram(
            @PathVariable Long programId,
            @Valid @RequestBody TrainingProgramDTO programDto) {
        log.info("Updating Training Program ID: {}", programId);
        return ResponseEntity.ok(programService.updateProgram(programId, programDto));
    }

    @DeleteMapping("/{programId}")
    public ResponseEntity<Void> deleteProgram(@PathVariable Long programId) {
        log.info("Deleting Training Program ID: {}", programId);
        programService.deleteProgram(programId);
        return ResponseEntity.noContent().build();
    }
}