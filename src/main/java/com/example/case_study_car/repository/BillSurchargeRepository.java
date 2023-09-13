package com.example.case_study_car.repository;


import com.example.case_study_car.domain.BillCarSurcharge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillSurchargeRepository extends JpaRepository<BillCarSurcharge, Long> {
}
