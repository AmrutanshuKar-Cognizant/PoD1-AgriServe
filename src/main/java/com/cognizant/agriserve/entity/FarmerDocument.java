package com.cognizant.agriserve.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="farmerDocument")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FarmerDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long documentId;

    private String docType;

    private String fileURI;

    private String verificationStatus;

    @ManyToOne
    @JoinColumn(name="farmerID")
    private Farmer farmer;


}
