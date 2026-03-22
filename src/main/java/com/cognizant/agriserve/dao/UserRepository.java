package com.cognizant.agriserve.dao;

import com.cognizant.agriserve.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    // Find user by email (used for login)
    Optional<User> findByEmail(String email);

    // Find user by phone
    Optional<User> findByPhone(String phone);

    // Find users by role
    List<User> findByRole(User.Role role);

    // Find active users
    List<User> findByStatus(String status);

    // Check if email already exists
    boolean existsByEmail(String email);

    // Check if phone already exists
    boolean existsByPhone(String phone);
}