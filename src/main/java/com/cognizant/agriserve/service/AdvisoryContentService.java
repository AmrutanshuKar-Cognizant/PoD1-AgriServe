package com.cognizant.agriserve.service;

import com.cognizant.agriserve.dto.AdvisoryContentRequestDTO;
import com.cognizant.agriserve.dto.AdvisoryContentResponseDTO;
import com.cognizant.agriserve.entity.AdvisoryContent;
import java.util.List;

public interface AdvisoryContentService {
    // Returns DTO to keep Entity private
    AdvisoryContentResponseDTO saveContent(AdvisoryContentRequestDTO content);

    // Returns a list of DTOs for the UI/Frontend
    List<AdvisoryContentResponseDTO> getAllActiveContent();

    void softDeleteContent(Long id);
}