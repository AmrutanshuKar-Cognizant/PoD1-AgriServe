package com.cognizant.agriserve.controller;

import com.cognizant.agriserve.dto.FarmerDTO;
import com.cognizant.agriserve.entity.Farmer;
import com.cognizant.agriserve.service.FarmerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/farmers")
public class FarmerController {

    @Autowired
    private FarmerService farmerService;

    @PostMapping
    public Farmer createFarmer(@RequestBody FarmerDTO dto)
    {
        return farmerService.createFarmer(dto);
    }

    @GetMapping("/{farmerId}")
    public Farmer getFarmer(@PathVariable Long farmerId)
    {
        return farmerService.getFarmerById(farmerId).orElseThrow(() -> new RuntimeException("Farmer not found"));
    }

    @PutMapping("/{farmerId}")
    public Farmer updateFarmer(@PathVariable Long farmerId, @RequestBody FarmerDTO dto)
    {
        return farmerService.updateFarmer(farmerId, dto);
    }
}
