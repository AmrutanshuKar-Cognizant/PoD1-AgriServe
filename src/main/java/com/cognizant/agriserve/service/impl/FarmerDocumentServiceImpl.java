package com.cognizant.agriserve.service.impl;

import com.cognizant.agriserve.dao.FarmerDocumentRepository;

import com.cognizant.agriserve.dao.FarmerRepository;

import com.cognizant.agriserve.dto.response.FarmerDocumentResponseDTO;

import com.cognizant.agriserve.dto.request.FarmerDocumentUploadRequestDTO;

import com.cognizant.agriserve.entity.Farmer;

import com.cognizant.agriserve.entity.FarmerDocument;

import com.cognizant.agriserve.exception.ResourceNotFoundException;
import com.cognizant.agriserve.service.FarmerDocumentService;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import java.util.List;

import java.util.stream.Collectors;

@Slf4j

@Service

@RequiredArgsConstructor

public class FarmerDocumentServiceImpl implements FarmerDocumentService {

    private final FarmerDocumentRepository farmerDocumentRepository;

    private final FarmerRepository farmerRepository;
    private final ModelMapper modelMapper;

    @Override

    @Transactional

    public FarmerDocumentResponseDTO uploadDocument(String email, FarmerDocumentUploadRequestDTO dto) {

        log.info("Processing document upload for farmer email: {}", email);

        // 1. Find the logged-in farmer

        Farmer farmer = farmerRepository.findByEmail(email)

                .orElseThrow(() -> new ResourceNotFoundException("Farmer profile not found for email: " + email));

        // 2. Create the new Document Entity

        FarmerDocument document = new FarmerDocument();

        document.setDocType(dto.getDocType());

        document.setFileURI(dto.getFileURI());

        // 3. Systematically handle the secure data

        document.setUploadedDate(LocalDate.now());

        document.setVerificationStatus(FarmerDocument.VerificationStatus.PENDING);

        document.setFarmer(farmer); // Establish the Foreign Key link

        // 4. Save to database

        FarmerDocument savedDocument = farmerDocumentRepository.save(document);

        log.info("Successfully uploaded document ID: {} for farmer ID: {}", savedDocument.getDocumentId(), farmer.getFarmerId());

        return mapToDto(savedDocument);

    }

    @Override

    public List<FarmerDocumentResponseDTO> getMyDocuments(String email) {

        log.debug("Fetching all documents for farmer email: {}", email);

        return farmerDocumentRepository.findByEmail(email).stream()

                .map(this::mapToDto)

                .collect(Collectors.toList());

    }

    @Override

    public List<FarmerDocumentResponseDTO> getAllPendingDocuments() {

        log.debug("Admin Request: Fetching all pending documents");

        return farmerDocumentRepository.findByVerificationStatus(FarmerDocument.VerificationStatus.PENDING)

                .stream()

                .map(this::mapToDto)

                .collect(Collectors.toList());

    }

    @Override

    @Transactional

    public FarmerDocumentResponseDTO updateDocumentStatus(Long documentId, String newStatus) {

        log.info("Admin Request: Updating status for document ID {} to {}", documentId, newStatus);

        // 1. Find the specific document by its ID

        FarmerDocument document = farmerDocumentRepository.findById(documentId)

                .orElseThrow(() -> new ResourceNotFoundException("Document not found with ID: " + documentId));

        // 2. Convert the incoming string to our strict Enum

        try {

            FarmerDocument.VerificationStatus statusEnum = FarmerDocument.VerificationStatus.valueOf(newStatus.toUpperCase());

            document.setVerificationStatus(statusEnum);

        } catch (IllegalArgumentException e) {

            throw new RuntimeException("Invalid status. Must be PENDING, VERIFIED, or REJECTED.");

        }

        // 3. Save the updated document

        FarmerDocument updatedDocument = farmerDocumentRepository.save(document);

        return mapToDto(updatedDocument);

    }


    private FarmerDocumentResponseDTO mapToDto(FarmerDocument document) {

        return modelMapper.map(document, FarmerDocumentResponseDTO.class);

    }

}
