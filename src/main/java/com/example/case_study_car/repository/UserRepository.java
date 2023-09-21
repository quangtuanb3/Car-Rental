package com.example.case_study_car.repository;
import com.example.case_study_car.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);

    boolean existsByEmailIgnoreCase(String email);




}