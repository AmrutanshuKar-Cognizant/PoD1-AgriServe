package com.cognizant.agriserve.entity;

import jakarta.persistence.*;
import org.apache.catalina.User;


@Entity
@Table(name="farmers")
public class Farmer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long farmerID;

    private String address;

    private Double landSize;

    private String cropType;

    private String status;

    @OneToOne
    @JoinColumn(name="user_id")
    private User user;

    public Farmer()
    {

    }

    public Long getFarmerID()
    {
        return farmerID;
    }

    public void setFarmerID(Long farmerID) {
        this.farmerID = farmerID;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address=address;
    }

    public Double getLandSize()
    {
        return landSize;
    }

    public void setLandSize(Double landSize) {
        this.landSize = landSize;
    }

    public String getCropType() {
        return cropType;
    }

    public void setCropType(String cropType) {
        this.cropType = cropType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
