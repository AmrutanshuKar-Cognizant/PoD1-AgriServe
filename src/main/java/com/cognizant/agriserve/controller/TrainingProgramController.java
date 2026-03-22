package com.cognizant.agriserve.controller;

import com.cognizant.agriserve.dto.TrainingProgramDto;
import com.cognizant.agriserve.service.TrainingProgramService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/programs") // The base URL for all endpoints in this file
public class TrainingProgramController {

    private final TrainingProgramService programService;

    // Constructor Injection connects this controller to your business logic
    public TrainingProgramController(TrainingProgramService programService) {
        this.programService = programService;
    }

    // --- ENDPOINT 1: Create a new Program ---
    @PostMapping
    public ResponseEntity<TrainingProgramDto> createProgram(@RequestBody TrainingProgramDto programDto) {
        // Send the incoming JSON data to the Service layer to be saved
        TrainingProgramDto savedProgram = programService.createProgram(programDto);

        // Return the saved data to the frontend with a "201 Created" status code
        return new ResponseEntity<>(savedProgram, HttpStatus.CREATED);
    }

    // --- ENDPOINT 2: Get all Programs ---
    @GetMapping
    public ResponseEntity<List<TrainingProgramDto>> getAllPrograms() {
        // Ask the Service layer for the list of all programs
        List<TrainingProgramDto> programs = programService.getAllPrograms();

        // Return the list with a "200 OK" status code
        return ResponseEntity.ok(programs);
    }
}