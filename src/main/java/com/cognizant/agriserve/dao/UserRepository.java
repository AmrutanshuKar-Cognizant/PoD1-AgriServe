package com.cognizant.agriserve.dao;

import com.cognizant.agriserve.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Spring Data JPA automatically provides findById, save, delete, etc.
    // You can add custom security/login methods here later if needed
    Optional<User> findByEmail(String email);
}