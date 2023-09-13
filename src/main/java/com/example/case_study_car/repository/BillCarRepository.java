package com.example.case_study_car.repository;


import com.example.case_study_car.domain.BillCar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillCarRepository extends JpaRepository<BillCar, Long> {
}
