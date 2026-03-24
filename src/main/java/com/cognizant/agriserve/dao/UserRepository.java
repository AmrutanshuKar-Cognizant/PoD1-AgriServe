package com.cognizant.agriserve.dao;

import com.cognizant.agriserve.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
<<<<<<< HEAD

public interface UserRepository extends JpaRepository<User,Long> {
}
=======
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    Optional<User> findByPhone(String phone);

    List<User> findByRole(User.Role role);

    List<User> findByStatus(String status);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);
}
>>>>>>> origin/dev
