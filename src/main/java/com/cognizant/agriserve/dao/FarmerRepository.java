package com.cognizant.agriserve.dao;

import com.cognizant.agriserve.entity.Farmer;  // refers to farmer entity table

import org.springframework.data.jpa.repository.JpaRepository; // provide built-in Db methods

import org.springframework.data.jpa.repository.Query;  // used to write custom SQL/JPQL query
import org.springframework.data.repository.query.Param;  //  used to pass parameters into query
import org.springframework.stereotype.Repository;

import java.util.Optional;

// tells spring this class handles database operations
@Repository
public interface FarmerRepository extends JpaRepository<Farmer, Long> {  // farmer = entity, Long = primary key

    @Query("SELECT f FROM Farmer f WHERE f.user.email = :email") // JPQL, runs query return farmer object

    Optional<Farmer> findByEmail(@Param("email") String email);

}
