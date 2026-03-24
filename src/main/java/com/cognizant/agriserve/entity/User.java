package com.cognizant.agriserve.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
public class User {
    public enum Role {
        Admin, ExtensionOfficer, ComplianceOfficer, Farmer, Auditor, ProgramManager
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // FIX 1: Change Integer to Long
    @Column(name = "user_id")
    private Long userID;

    private String name;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String email;
    private String phone;
    private String password;
    private String status;

    // FIX 2: Return type is already Long, so this is now compatible
    public Long getUserID() {
        return userID;
    }

    // FIX 3: Change parameter from Integer to Long
    public void setUserID(Long userID) {
        this.userID = userID;
    }

    // ... (Keep the rest of your getters and setters as they were)

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}