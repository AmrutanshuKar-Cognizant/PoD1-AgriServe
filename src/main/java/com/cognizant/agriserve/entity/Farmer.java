package com.cognizant.agriserve.entity; // all db tables are store here

import jakarta.persistence.*;  //used for JPA. ex: @Entity, @Id, @Column

import jakarta.validation.constraints.*;  //used for validation rules. ex: @NotBlank, @NotNull, used bcoz prevent invalid data before saving

import lombok.*;  // it reduces boilerplate code, automatically creates getter, setter, constructor

import org.hibernate.annotations.CreationTimestamp; // both are automatically sets created time and updated time
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity  // marks class as db table
@Table(name = "farmer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Farmer {
    public enum Status {
        PENDING,
        ACTIVE,
        INACTIVE,
        SUSPENDED
    }

    public enum Gender{
        MALE,
        FEMALE,
        OTHER
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long farmerId;

    @NotBlank(message = "Name cannot be empty")
    @Column(nullable = false, length = 100)
    private String name;

    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    private LocalDate dob;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @NotBlank(message = "Address is required")
    @Column(columnDefinition = "TEXT")
    private String address;

    @NotBlank(message = "Contact info is required")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Invalid phone number format")
    @Column(unique = true, nullable = false)
    private String contactInfo;

    @PositiveOrZero(message = "Land size cannot be negative")
    private Double landSize;

    private String cropType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.PENDING;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}