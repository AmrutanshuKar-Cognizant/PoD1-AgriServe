package com.cognizant.agriserve.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    public enum Role {
        Admin, ExtensionOfficer, ComplianceOfficer,Farmer,Auditor,ProgramManager
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userID;

    private String name;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String email;

    private String phone;

    private String password;

    private String status;


}