package com.cognizant.agriserve.entity;

import jakarta.persistence.*;
import com.cognizant.agriserve.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "farmer")
@Data
@NoArgsConstructor
@AllArgsConstructor
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


}