package com.cognizant.agriserve.service.impl;

import com.cognizant.agriserve.dao.AdvisoryContentRepository;
import com.cognizant.agriserve.entity.AdvisoryContent;
import com.cognizant.agriserve.service.AdvisoryContentService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
@RequiredArgsConstructor
@Service

public class AdvisoryContentServiceImpl implements AdvisoryContentService {

    private final AdvisoryContentRepository contentRepo;

    @Override
    public AdvisoryContent saveContent(AdvisoryContent content) {
        return contentRepo.save(content);
    }

    @Override
    public List<AdvisoryContent> getAllActiveContent() {
        // Uses the Repository method you wrote to filter for 'Active' status
        return contentRepo.findByStatus("Active");
    }

    @Override
    public void softDeleteContent(Integer id) {
        AdvisoryContent content = contentRepo.findById(id).orElse(null);
        if (content != null) {
            content.setStatus("Inactive");
            contentRepo.save(content);
        }
    }
}