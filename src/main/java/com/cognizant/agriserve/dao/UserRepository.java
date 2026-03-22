package com.cognizant.agriserve.dao;

import com.cognizant.agriserve.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
