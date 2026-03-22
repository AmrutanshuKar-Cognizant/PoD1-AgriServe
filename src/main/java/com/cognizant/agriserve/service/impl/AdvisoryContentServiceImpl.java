package com.cognizant.agriserve.service.impl;

import com.cognizant.agriserve.dao.AdvisoryContentRepository;
import com.cognizant.agriserve.dto.AdvisoryContentResponseDTO;
import com.cognizant.agriserve.entity.AdvisoryContent;
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
    public AdvisoryContentResponseDTO saveContent(AdvisoryContent content) {
        if (content.getStatus() == null) content.setStatus("Active");
        AdvisoryContent saved = contentRepo.save(content);
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
        contentRepo.findById(id).ifPresent(c -> {
            c.setStatus("Inactive");
            contentRepo.save(c);
        });
    }
}