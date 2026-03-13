package com.cognizant.agriserve.entity;

import jakarta.persistence.*;

@Entity
@Table(name="farmer_documents")
public class FarmerDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long documentId;

    private String docType;

    private String fileURI;

    private String verificationStatus;

    @ManyToOne
    @JoinColumn(name="farmer_id")
    private Farmer farmer;

    public FarmerDocument()
    {

    }

    public Long getDocumentId()
    {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getFileURI() {
        return fileURI;
    }

    public void setFileURI(String fileURI) {
        this.fileURI = fileURI;
    }

    public String getVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(String verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

    public Farmer getFarmer() {
        return farmer;
    }

    public void setFarmer(Farmer farmer) {
        this.farmer = farmer;
    }
}
