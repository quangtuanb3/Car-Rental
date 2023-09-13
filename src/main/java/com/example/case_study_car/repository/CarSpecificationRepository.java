package com.example.case_study_car.repository;


import com.example.case_study_car.domain.CarSpecification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarSpecificationRepository extends JpaRepository<CarSpecification, Long> {
}
