package com.cognizant.agriserve.dao;

import com.cognizant.agriserve.entity.FarmerDocument;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface FarmerDocumentRepository extends JpaRepository<FarmerDocument, Long> {

    @Query("SELECT fd FROM FarmerDocument fd WHERE fd.farmer.user.email = :email")
    List<FarmerDocument> findByEmail(@Param("email") String email);

    List<FarmerDocument> findByVerificationStatus(FarmerDocument.VerificationStatus status);
}
