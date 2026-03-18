package com.cognizant.agriserve.service;

import com.cognizant.agriserve.dto.FarmerDocumentDTO;
import com.cognizant.agriserve.entity.FarmerDocument;
import com.cognizant.agriserve.entity.Farmer;
import java.util.List;

public interface FarmerDocumentService {

    FarmerDocument uploadDocument(FarmerDocumentDTO dto);

    Farmer verifyFarmer(Long farmerId); //admin verification
}
