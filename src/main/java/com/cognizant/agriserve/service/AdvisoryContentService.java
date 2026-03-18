package com.cognizant.agriserve.service;

import com.cognizant.agriserve.entity.AdvisoryContent;
import java.util.List;

public interface AdvisoryContentService {
    AdvisoryContent saveContent(AdvisoryContent content);
    List<AdvisoryContent> getAllActiveContent();
    void softDeleteContent(Integer id);
}