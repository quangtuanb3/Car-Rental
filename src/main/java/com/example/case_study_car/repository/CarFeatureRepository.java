package com.example.case_study_car.repository;


import com.example.case_study_car.domain.CarFeature;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarFeatureRepository extends JpaRepository<CarFeature, Long> {
}
