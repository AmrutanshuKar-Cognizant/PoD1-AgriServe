package com.cognizant.agriserve.service;

import com.cognizant.agriserve.entity.*;
import com.cognizant.agriserve.dao.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class FarmerSerive {

    @Autowired
    private FarmerRepository farmerRepository;

    @Autowired
    private FarmerDocumentRepository farmerDocumentRepository;

    @Autowired
    private UserRepository userRepository;

    public Farmer createFarmerProfile(Long userId, Farmer farmer)
    {
        User user=userRepository.findById(userId).orElseThrow();

        farmer.setUser(user);
        farmer.setStatus("Pending");
    }
}
