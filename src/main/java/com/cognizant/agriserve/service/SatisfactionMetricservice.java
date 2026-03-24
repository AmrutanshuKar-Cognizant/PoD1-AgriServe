package com.cognizant.agriserve.service;

import com.cognizant.agriserve.dao.*;
import com.cognizant.agriserve.dto.SatisfactionMetricDTO;
import com.cognizant.agriserve.entity.*;

public interface SatisfactionMetricservice {
    SatisfactionMetric evaluate(SatisfactionMetricDTO dto);
}