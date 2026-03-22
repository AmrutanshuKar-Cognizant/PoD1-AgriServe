package com.cognizant.agriserve.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "auditLog")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer auditID;

    @ManyToOne
    @JoinColumn(name = "userID")
    private User user;

    private String action;

    private String resource;

    private LocalDateTime timestamp;


}