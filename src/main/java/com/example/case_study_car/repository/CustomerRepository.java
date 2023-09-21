package com.example.case_study_car.repository;


import com.example.case_study_car.domain.Customer;
import com.example.case_study_car.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findCustomerByEmail(String email);
    boolean existsByEmailIgnoreCase(String email);
    boolean existsByNumberPhone(String numberPhone);


}
