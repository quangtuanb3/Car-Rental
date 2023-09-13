package com.example.case_study_car.repository;


import com.example.case_study_car.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecificationRepository extends JpaRepository<Specification, Long> {
}
