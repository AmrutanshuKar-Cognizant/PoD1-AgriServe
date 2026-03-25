package com.cognizant.agriserve.service;

import com.cognizant.agriserve.dto.request.SatisfactionMetricRequestDTO;
import com.cognizant.agriserve.dto.response.SatisfactionMetricResponseDTO;
import com.cognizant.agriserve.entity.*;

import java.util.List;

public interface SatisfactionMetricservice {
    SatisfactionMetric evaluate(SatisfactionMetricRequestDTO dto);

    List<SatisfactionMetricResponseDTO> getSatisfactionmetric();
}