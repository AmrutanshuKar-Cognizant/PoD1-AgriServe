package com.cognizant.agriserve.service.impl;

import com.cognizant.agriserve.dao.AdvisoryContentRepository;
import com.cognizant.agriserve.dto.AdvisoryContentRequestDTO;
import com.cognizant.agriserve.dto.AdvisoryContentResponseDTO;
import com.cognizant.agriserve.entity.AdvisoryContent;
import com.cognizant.agriserve.exception.ResourceNotFoundException;
import com.cognizant.agriserve.service.AdvisoryContentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdvisoryContentServiceImpl implements AdvisoryContentService {

    private final AdvisoryContentRepository contentRepo;
    private final ModelMapper modelMapper;

    @Override
    public AdvisoryContentResponseDTO saveContent(AdvisoryContentRequestDTO requestDto) {
        // 1. Convert DTO to Entity
        AdvisoryContent content = modelMapper.map(requestDto, AdvisoryContent.class);

        // 2. Set default server-side values
        content.setStatus("Active");
        content.setUploadedDate(java.time.LocalDateTime.now());

        // 3. Save to database
        AdvisoryContent saved = contentRepo.save(content);

        // 4. Convert saved Entity back to ResponseDTO
        return modelMapper.map(saved, AdvisoryContentResponseDTO.class);
    }

    @Override
    public List<AdvisoryContentResponseDTO> getAllActiveContent() {
        return contentRepo.findByStatus("Active").stream()
                .map(content -> modelMapper.map(content, AdvisoryContentResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void softDeleteContent(Long id) {
        AdvisoryContent content=contentRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cannot delete. Advisory Content not found with ID: " + id));
        content.setStatus("Inactive");
        contentRepo.save(content);
//        contentRepo.findById(id).ifPresent(c -> {
//            c.setStatus("Inactive");
//            contentRepo.save(c);
//        });
    }
}