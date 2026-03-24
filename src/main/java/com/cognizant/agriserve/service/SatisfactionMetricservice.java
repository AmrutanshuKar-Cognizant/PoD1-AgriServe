package com.cognizant.agriserve.service;

import com.cognizant.agriserve.dao.*;
import com.cognizant.agriserve.dto.SatisfactionMetricDTO;
import com.cognizant.agriserve.dto.SatisfactionMetricResponseDTO;
import com.cognizant.agriserve.entity.*;

import java.util.List;

public interface SatisfactionMetricservice {
    SatisfactionMetric evaluate(SatisfactionMetricDTO dto);

    List<SatisfactionMetricResponseDTO> getSatisfactionmetric();
}