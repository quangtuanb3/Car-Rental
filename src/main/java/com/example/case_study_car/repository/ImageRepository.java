package com.example.case_study_car.repository;


import com.example.case_study_car.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
