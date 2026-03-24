package com.cognizant.agriserve.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuditLogResponseDTO {

    private Integer auditId;
    private Integer userId;
    private String userName;
    private String action;
    private String resource;
    private LocalDateTime timestamp;
}