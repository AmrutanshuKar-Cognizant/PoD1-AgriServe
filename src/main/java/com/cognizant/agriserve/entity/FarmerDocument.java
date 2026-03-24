package com.cognizant.agriserve.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "farmerDocument") // Standard plural naming

@Getter  // automatically created getter and setter
@Setter

// creates empty constructor like -- Farmer farmer=new Farmer();
// it is used bcoz of hibernate needs a default constructor to create entity objects
@NoArgsConstructor

// creates constructor with all fields -- Farmer farmer=new Farmer(id, name, dob, gender,...);
// easy to create object with all values
@AllArgsConstructor

// provides clean way to create objects
@Builder

// it restricts values to fixed set, without enum user can enter anything
public class FarmerDocument {
    public enum VerificationStatus{
        PENDING,
        VERIFIED,
        REJECTED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //primary key value should be generated automatically and GenerationType is type of auto-generation
    // in table looks like (documentId INT AUTO_INCREMENT PRIMARY KEY)
    private Long documentId;

    @NotBlank(message = "Document type is mandatory")
    @Column(nullable = false)
    private String docType; // e.g., Aadhar, Land Permit, etc.

    @NotBlank(message = "File URI cannot be empty")
    @Column(nullable = false, unique = true)
    private String fileURI;

    @NotNull(message = "Upload date is required")
    @PastOrPresent(message = "Upload date cannot be in the future")
    private LocalDate uploadedDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VerificationStatus verificationStatus = VerificationStatus.PENDING;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "farmer_id", nullable = false)
    @NotNull(message = "Associated farmer is required")
    private Farmer farmer;

    @CreationTimestamp  // automatically set when record is created
    @Column(updatable = false)  // cannot be updated
    private LocalDateTime createdAt;
}