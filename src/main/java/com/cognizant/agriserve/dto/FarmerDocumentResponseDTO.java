package com.cognizant.agriserve.dto;

import lombok.Data;
import java.time.LocalDate;

@Data

public class FarmerDocumentResponseDTO {

    private Long documentId;

    private String docType;

    private String fileURI;

    private LocalDate uploadedDate;

    private String verificationStatus;

}
