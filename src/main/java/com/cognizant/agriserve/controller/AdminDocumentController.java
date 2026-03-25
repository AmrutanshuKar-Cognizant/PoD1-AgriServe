package com.cognizant.agriserve.controller;

import com.cognizant.agriserve.dto.response.FarmerDocumentResponseDTO;

import com.cognizant.agriserve.service.FarmerDocumentService;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/admin/documents")
@RequiredArgsConstructor
public class AdminDocumentController {

    private final FarmerDocumentService farmerDocumentService;

    @GetMapping("/pending")
    public ResponseEntity<List<FarmerDocumentResponseDTO>> getPendingDocuments() {

        log.info("API Request: Admin fetching all pending documents");
        List<FarmerDocumentResponseDTO> pendingDocs = farmerDocumentService.getAllPendingDocuments();
        return ResponseEntity.ok(pendingDocs);
    }


    @PutMapping("/{documentId}/verify")
    public ResponseEntity<FarmerDocumentResponseDTO> verifyDocument(
            @PathVariable Long documentId,
            @RequestParam String status) {
        log.info("API Request: Admin updating document ID {} to status {}", documentId, status);
        FarmerDocumentResponseDTO updatedDocument = farmerDocumentService.updateDocumentStatus(documentId, status);
        return ResponseEntity.ok(updatedDocument);
    }
}
