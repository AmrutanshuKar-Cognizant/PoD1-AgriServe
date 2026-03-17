package com.cognizant.agriserve.service;

import com.cognizant.agriserve.entity.FarmerDocument;
import com.cognizant.agriserve.entity.Farmer;
import java.util.List;

public interface FarmerDocumentService {

    FarmerDocument uploadDocument(Long farmerId, String docType, String fileURI);

    Farmer verifyFarmer(Long farmerId); //admin verification
}
