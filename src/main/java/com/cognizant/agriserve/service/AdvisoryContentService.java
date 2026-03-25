package com.cognizant.agriserve.service;

import com.cognizant.agriserve.dto.request.AdvisoryContentRequestDTO;
import com.cognizant.agriserve.dto.response.AdvisoryContentResponseDTO;
import com.cognizant.agriserve.entity.AdvisoryContent;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface AdvisoryContentService {
    // Returns DTO to keep Entity private
    AdvisoryContentResponseDTO saveContent(AdvisoryContentRequestDTO content, Authentication authentication);

    // Returns a list of DTOs for the UI/Frontend
    List<AdvisoryContentResponseDTO> getAllActiveContent();

    void softDeleteContent(Long id,Authentication authentication);
}