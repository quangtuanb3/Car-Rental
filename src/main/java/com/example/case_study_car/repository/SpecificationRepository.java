package com.example.case_study_car.repository;


import com.example.case_study_car.domain.Specification;
import com.example.case_study_car.domain.enumaration.ESpecificationType;
import com.example.case_study_car.service.response.SelectOptionResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Arrays;
import java.util.List;
import java.util.Queue;

public interface SpecificationRepository extends JpaRepository<Specification, Long> {
    @Query(value = "SELECT s.type FROM Specification s GROUP BY s.type")
    List<String> findAllType();

    List<Specification> getSpecificationByType(ESpecificationType type);
}
