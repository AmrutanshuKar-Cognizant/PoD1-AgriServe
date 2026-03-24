package com.cognizant.agriserve.dao;

import com.cognizant.agriserve.entity.Farmer;
<<<<<<< HEAD
import org.springframework.data.jpa.repository.JpaRepository;

public interface FarmerRepository extends JpaRepository<Farmer,Long> {
=======

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface FarmerRepository extends JpaRepository<Farmer, Long> {

    @Query("SELECT f FROM Farmer f WHERE f.user.email = :email")

    Optional<Farmer> findByEmail(@Param("email") String email);

>>>>>>> origin/dev
}
