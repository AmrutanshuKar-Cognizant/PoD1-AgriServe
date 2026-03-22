package com.cognizant.agriserve.entity;

import jakarta.persistence.*;
import com.cognizant.agriserve.entity.User;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "farmer")
public class Farmer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long farmerId;

    private String name;
    private String dob;
    private String gender;

    private String address;
    private String contactInfo;

    private Double landSize;
    private String cropType;

    private String status;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "farmer")
    List<AdvisorySession> sessions=new ArrayList<>();

    // 1. Default Constructor (MANDATORY for JPA)
//    public Farmer() {
//    }
//
//    // 2. Parameterized Constructor (WITH ID)
//    public Farmer(String name, String dob, String gender,
//                  String address, String contactInfo, Double landSize,
//                  String cropType, String status, User user) {
//
//        this.name = name;
//        this.dob = dob;
//        this.gender = gender;
//        this.address = address;
//        this.contactInfo = contactInfo;
//        this.landSize = landSize;
//        this.cropType = cropType;
//        this.status = status;
//        this.user = user;
//    }
//
//
//    // Getters and Setters
//
//    public Long getFarmerId() {
//        return farmerId;
//    }
//
//    public void setFarmerId(Long farmerId) {
//        this.farmerId = farmerId;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getDob() {
//        return dob;
//    }
//
//    public void setDob(String dob) {
//        this.dob = dob;
//    }
//
//    public String getGender() {
//        return gender;
//    }
//
//    public void setGender(String gender) {
//        this.gender = gender;
//    }
//
//    public String getAddress() {
//        return address;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }
//
//    public String getContactInfo() {
//        return contactInfo;
//    }
//
//    public void setContactInfo(String contactInfo) {
//        this.contactInfo = contactInfo;
//    }
//
//    public Double getLandSize() {
//        return landSize;
//    }
//
//    public void setLandSize(Double landSize) {
//        this.landSize = landSize;
//    }
//
//    public String getCropType() {
//        return cropType;
//    }
//
//    public void setCropType(String cropType) {
//        this.cropType = cropType;
//    }
//
//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
//
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
}