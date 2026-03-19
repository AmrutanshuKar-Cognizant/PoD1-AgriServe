package com.cognizant.agriserve.controller;

import com.cognizant.agriserve.dto.FarmerDocumentDTO;
import com.cognizant.agriserve.entity.Farmer;
import com.cognizant.agriserve.entity.FarmerDocument;
import com.cognizant.agriserve.service.FarmerDocumentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController  //create rest api return data in json format
@RequestMapping("/api/documents")  //base url
public class FarmerDocumentController {

    @Autowired  //inject service layer automatically
    private FarmerDocumentService service;

    @PostMapping   //create and upload data
    public FarmerDocument uploadDocument(@RequestBody FarmerDocumentDTO dto)
    {
        return service.uploadDocument(dto);
    }

    @PutMapping("/verify/{farmerId}")   //update data gets verification status
    public Farmer verifyFarmer(@PathVariable Long farmerId)
    {
        return service.verifyFarmer(farmerId);
    }
}
