package com.cognizant.agriserve.controller;

import com.cognizant.agriserve.dto.FarmerDocumentResponseDTO;  // DTO is used for request input and response output
import com.cognizant.agriserve.dto.FarmerDocumentUploadRequestDto;

import com.cognizant.agriserve.service.FarmerDocumentService; // for business logic

import jakarta.validation.Valid; // enables validation on request body

import lombok.RequiredArgsConstructor;  // automatically creates constructor for all final fields
import lombok.extern.slf4j.Slf4j;  // automatically creates logger object

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*; // spring annotation for REST API's

import java.security.Principal; // used to get logged-in user email
import java.util.List;

@Slf4j

@RestController  // combines @Controller and @ResponseBody and returns JSON response automatically

@RequestMapping("/api/farmers/documents")  // base URL for all API's

@RequiredArgsConstructor
public class FarmerDocumentController {

    // service is injected
    // lombok generates:  public FarmerController(FarmerService farmerService)
    // { this.farmerService=farmerService; }
    private final FarmerDocumentService farmerDocumentService;

//  API 1: is for upload document
    @PostMapping
    public ResponseEntity<FarmerDocumentResponseDTO> uploadDocument(  // DTO response

            Principal principal,  // gets logged-in user

            @Valid @RequestBody FarmerDocumentUploadRequestDto requestDto) {  // @RequestBody -> JSON - DTO, @Valid -> validation applied

        String email = principal.getName();  // gets user email from JWT

        log.info("API Request: Uploading new document for farmer: {}", email);  // logs activity

        // calls service layer passes email, request data, from here control goes to Service Layer
        FarmerDocumentResponseDTO uploadedDocument = farmerDocumentService.uploadDocument(email, requestDto);

        // returns data, HTTP 201 (created)
        return new ResponseEntity<>(uploadedDocument, HttpStatus.CREATED);

    }


// API 2: get my documents
    @GetMapping
    public ResponseEntity<List<FarmerDocumentResponseDTO>> getMyDocuments(Principal principal) {  // returns list of documents

        String email = principal.getName();  // get logged-in user

        log.info("API Request: Fetching all documents for farmer: {}", email);

        List<FarmerDocumentResponseDTO> documents = farmerDocumentService.getMyDocuments(email); // fetch documents of that user

        return ResponseEntity.ok(documents);  // return HTTP 200

    }

}
