package com.cognizant.agriserve.service;

import com.cognizant.agriserve.dto.response.FarmerDocumentResponseDTO;
import com.cognizant.agriserve.dto.request.FarmerDocumentUploadRequestDTO;
import java.util.List;

public interface FarmerDocumentService {

    // --- FARMER METHODS ---

    // For the logged-in Farmer to upload a new document

    FarmerDocumentResponseDTO uploadDocument(String email, FarmerDocumentUploadRequestDTO requestDto);

    // For the logged-in Farmer to view all their uploaded documents

    List<FarmerDocumentResponseDTO> getMyDocuments(String email);

    // --- ADMIN / EXTENSION OFFICER METHODS ---

    // Fetches a list of all documents in the system that need to be reviewed

    List<FarmerDocumentResponseDTO> getAllPendingDocuments();

    // Allows the admin to change a document's status to VERIFIED or REJECTED

    FarmerDocumentResponseDTO updateDocumentStatus(Long documentId, String newStatus);

}
