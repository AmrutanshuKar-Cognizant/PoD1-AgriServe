package com.cognizant.agriserve.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name="farmer_Document")
public class FarmerDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long documentId;

    private String docType;

    private String fileURI;

    private LocalDate uploadedDate;
    private String verificationStatus;

    @ManyToOne
    @JoinColumn(name="farmerID")
    private Farmer farmer;

    public FarmerDocument()
    {

    }

    public FarmerDocument(String docType, String fileURI, LocalDate uploadedDate, String verificationStatus, Farmer farmer)
    {
        this.docType=docType;
        this.fileURI=fileURI;
        this.uploadedDate=uploadedDate;
        this.verificationStatus=verificationStatus;
        this.farmer=farmer;
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

    public LocalDate getUploadedDate() {
        return uploadedDate;
    }

    public void setUploadedDate(LocalDate uploadedDate) {
        this.uploadedDate = uploadedDate;
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
