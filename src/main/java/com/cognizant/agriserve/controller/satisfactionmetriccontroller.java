package com.cognizant.agriserve.controller;

import com.cognizant.agriserve.dto.SatisfactionMetricDTO;
import com.cognizant.agriserve.entity.SatisfactionMetric;
import com.cognizant.agriserve.service.SatisfactionMetricservice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/Satisfactionmetric")
public class satisfactionmetriccontroller {

    @Autowired
    private SatisfactionMetricservice metricservice;

    @PostMapping("/evaluate")
    public ResponseEntity<SatisfactionMetric> evaluatefeedback(@RequestBody SatisfactionMetricDTO dt) {
        log.info("REST request to evaluate performance for Program ID: {}", dt.getProgramId());
        return ResponseEntity.ok(metricservice.evaluate(dt));
    }
}