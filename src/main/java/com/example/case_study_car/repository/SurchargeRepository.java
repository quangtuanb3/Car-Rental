package com.example.case_study_car.repository;


import com.example.case_study_car.domain.Surcharge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurchargeRepository extends JpaRepository<Surcharge, Long> {
}
