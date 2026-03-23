package com.cognizant.agriserve.controller;

import com.cognizant.agriserve.dto.FarmerDocumentResponseDTO;
import com.cognizant.agriserve.dto.FarmerDocumentUploadRequestDto;

import com.cognizant.agriserve.service.FarmerDocumentService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import java.util.List;

@Slf4j

@RestController

@RequestMapping("/api/farmers/documents")

@RequiredArgsConstructor

public class FarmerDocumentController {

    private final FarmerDocumentService farmerDocumentService;

    /**

     * POST /api/farmers/documents

     * Allows a logged-in farmer to securely upload a new document link.

     */

    @PostMapping

    public ResponseEntity<FarmerDocumentResponseDTO> uploadDocument(

            Principal principal,

            @Valid @RequestBody FarmerDocumentUploadRequestDto requestDto) {

        String email = principal.getName();

        log.info("API Request: Uploading new document for farmer: {}", email);

        FarmerDocumentResponseDTO uploadedDocument = farmerDocumentService.uploadDocument(email, requestDto);

        return new ResponseEntity<>(uploadedDocument, HttpStatus.CREATED);

    }

    /**

     * GET /api/farmers/documents

     * Fetches all documents uploaded by the currently logged-in farmer.

     */

    @GetMapping

    public ResponseEntity<List<FarmerDocumentResponseDTO>> getMyDocuments(Principal principal) {

        String email = principal.getName();

        log.info("API Request: Fetching all documents for farmer: {}", email);

        List<FarmerDocumentResponseDTO> documents = farmerDocumentService.getMyDocuments(email);

        return ResponseEntity.ok(documents);

    }

}
