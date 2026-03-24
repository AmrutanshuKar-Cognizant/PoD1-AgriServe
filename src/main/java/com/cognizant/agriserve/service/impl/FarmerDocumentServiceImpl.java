package com.cognizant.agriserve.service.impl;

import com.cognizant.agriserve.dao.FarmerDocumentRepository;
import com.cognizant.agriserve.dao.FarmerRepository;
import com.cognizant.agriserve.dto.FarmerDocumentResponseDTO;
import com.cognizant.agriserve.dto.FarmerDocumentUploadRequestDto;
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

@Slf4j  // enable logging
@Service  // marks this as business logic layer

@RequiredArgsConstructor // automatically injects dependencies

public class FarmerDocumentServiceImpl implements FarmerDocumentService {  // this class implements interface

    // dependencies used to access DB
    private final FarmerDocumentRepository farmerDocumentRepository;

    private final FarmerRepository farmerRepository;
    private final ModelMapper modelMapper;

    @Override

    @Transactional

    // Method 1: uploadDocument()
    public FarmerDocumentResponseDTO uploadDocument(String email, FarmerDocumentUploadRequestDto dto) {

        log.info("Processing document upload for farmer email: {}", email);

        // 1. Find the logged-in farmer, if profile found calls DAO returns farmer object, otherwise throw exception
        Farmer farmer = farmerRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Farmer profile not found for email: " + email));

        // 2. Create the new Document object
        FarmerDocument document = new FarmerDocument();

        document.setDocType(dto.getDocType());
        document.setFileURI(dto.getFileURI());

        // 3. Systematically handle the secure data
        document.setUploadedDate(LocalDate.now());  // auto current date

        document.setVerificationStatus(FarmerDocument.VerificationStatus.PENDING); // default status

        document.setFarmer(farmer); // creates foreign key relationship

        // 4. Save to database
        FarmerDocument savedDocument = farmerDocumentRepository.save(document);

        // log success, saved object with ID
        log.info("Successfully uploaded document ID: {} for farmer ID: {}", savedDocument.getDocumentId(), farmer.getFarmerId());

        return mapToDto(savedDocument);  // response to DTO

    }

    // Method 2: getMyDocuments()
    @Override
    public List<FarmerDocumentResponseDTO> getMyDocuments(String email) {

        log.debug("Fetching all documents for farmer email: {}", email);

        return farmerDocumentRepository.findByEmail(email)  // fetch from DB

                // convert to DTO
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

    }

    // Method 3: getAllPendingDocuments()
    @Override
    public List<FarmerDocumentResponseDTO> getAllPendingDocuments() {

        log.debug("Admin Request: Fetching all pending documents");

        // DAO call findByVerificationStatus(PENDING)
        return farmerDocumentRepository.findByVerificationStatus(FarmerDocument.VerificationStatus.PENDING)  // fetch only pending documents

                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList()); // response is list of pending docs

    }

    // Method 4: updateDocumentsStatus()
    @Override
    @Transactional
    public FarmerDocumentResponseDTO updateDocumentStatus(Long documentId, String newStatus) {

        log.info("Admin Request: Updating status for document ID {} to {}", documentId, newStatus);

        // 1. Find the specific document by its ID (we need to find the document id to update the specific doc)
        FarmerDocument document = farmerDocumentRepository.findById(documentId).orElseThrow(() -> new ResourceNotFoundException("Document not found with ID: " + documentId));

        // 2. Convert the incoming string to our strict Enum
        try {

            FarmerDocument.VerificationStatus statusEnum = FarmerDocument.VerificationStatus.valueOf(newStatus.toUpperCase());

            document.setVerificationStatus(statusEnum);

        } catch (IllegalArgumentException e) {

            throw new RuntimeException("Invalid status. Must be PENDING, VERIFIED, or REJECTED.");

        }

        // 3. Save the updated document
        FarmerDocument updatedDocument = farmerDocumentRepository.save(document);

        // 4. Return to DTO
        return mapToDto(updatedDocument);

    }


    private FarmerDocumentResponseDTO mapToDto(FarmerDocument document) {

        return modelMapper.map(document, FarmerDocumentResponseDTO.class);

    }

}
