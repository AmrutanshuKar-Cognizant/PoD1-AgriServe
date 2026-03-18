package com.cognizant.agriserve.service.impl;

import com.cognizant.agriserve.dao.FarmerDocumentRepository;
import com.cognizant.agriserve.dao.FarmerRepository;
import com.cognizant.agriserve.dto.FarmerDocumentDTO;
import com.cognizant.agriserve.entity.Farmer;
import com.cognizant.agriserve.entity.FarmerDocument;
import com.cognizant.agriserve.service.FarmerDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import java.util.List;

@Service
public class FarmerDocumentServiceImpl implements FarmerDocumentService {

    @Autowired
    private FarmerRepository farmerRepository;

    @Autowired
    private FarmerDocumentRepository farmerDocumentRepository;

    @Override
    public FarmerDocument uploadDocument(FarmerDocumentDTO dto) {
        Farmer farmer = farmerRepository.findById(dto.getFarmerId()).orElseThrow(() -> new RuntimeException("Farmer not found"));

        FarmerDocument document = new FarmerDocument(dto.getDocType(), dto.getFileURI(), java.time.LocalDate.now(), "Pending", farmer);

        {
            return farmerDocumentRepository.save(document);
        }
    }



    @Override
    public Farmer verifyFarmer(Long farmerId)
    {
        Farmer farmer=farmerRepository.findById(farmerId).orElseThrow(() -> new RuntimeException("Farmer not found"));

        farmer.setStatus("Active");

        return farmerRepository.save(farmer);
    }
}
