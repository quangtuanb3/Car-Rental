package com.example.case_study_car.repository;


import com.example.case_study_car.domain.Bill;
import com.example.case_study_car.domain.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BillRepository extends JpaRepository<Bill, Long> {

    @Query("SELECT b FROM Bill b")
    Page<Bill> pageEverything(Pageable pageable);
}
