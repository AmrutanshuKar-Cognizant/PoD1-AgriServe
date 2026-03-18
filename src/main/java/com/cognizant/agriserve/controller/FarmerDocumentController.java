package com.cognizant.agriserve.controller;

import com.cognizant.agriserve.dto.FarmerDocumentDTO;
import com.cognizant.agriserve.entity.Farmer;
import com.cognizant.agriserve.entity.FarmerDocument;
import com.cognizant.agriserve.service.FarmerDocumentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/documents")
public class FarmerDocumentController {

    @Autowired
    private FarmerDocumentService service;

    @PostMapping
    public FarmerDocument uploadDocument(@RequestBody FarmerDocumentDTO dto)
    {
        return service.uploadDocument(dto);
    }

    @PutMapping("/verify/{farmerId}")
    public Farmer verifyFarmer(@PathVariable Long farmerId)
    {
        return service.verifyFarmer(farmerId);
    }
}
