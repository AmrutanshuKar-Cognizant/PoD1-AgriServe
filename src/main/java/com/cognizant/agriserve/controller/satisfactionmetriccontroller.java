package com.cognizant.agriserve.controller;

import com.cognizant.agriserve.dto.request.SatisfactionMetricRequestDTO;
import com.cognizant.agriserve.dto.response.SatisfactionMetricResponseDTO;
import com.cognizant.agriserve.entity.SatisfactionMetric;
import com.cognizant.agriserve.service.SatisfactionMetricservice;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/Satisfactionmetric")
public class satisfactionmetriccontroller {

    @Autowired
    private SatisfactionMetricservice metricservice;

    @PostMapping("/evaluate")
    public ResponseEntity<SatisfactionMetric> evaluatefeedback(@RequestBody @Valid SatisfactionMetricRequestDTO dt) {
        log.info("REST request to evaluate performance for Program ID: {}", dt.getProgramId());
        return ResponseEntity.ok(metricservice.evaluate(dt));
    }

    @GetMapping("/getSpecs")
    public List<SatisfactionMetricResponseDTO> giveaway(){
        log.info("REST response to send performance for Given Program ID");
        return metricservice.getSatisfactionmetric();
    }
}