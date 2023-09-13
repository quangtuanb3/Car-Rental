package com.example.case_study_car.repository;


import com.example.case_study_car.domain.CarSurcharge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarSurchargeRepository extends JpaRepository<CarSurcharge, Long> {
}
