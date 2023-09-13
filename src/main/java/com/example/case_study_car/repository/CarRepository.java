package com.example.case_study_car.repository;


import com.example.case_study_car.domain.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {
}
