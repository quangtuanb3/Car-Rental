package com.example.case_study_car.repository;


import com.example.case_study_car.domain.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CarRepository extends JpaRepository<Car, Long> {

    @Query(value = "SELECT c FROM Car c " +
            "WHERE " +
            "c.name LIKE :search OR " +
            "c.description LIKE :search OR " +
            "c.agency.name LIKE :search " +
            " OR EXISTS (SELECT 1 FROM CarSpecification cs WHERE cs.car = c AND cs.specification.name LIKE :search)" +
            " OR EXISTS (SELECT 1 FROM CarFeature cf WHERE cf.car = c AND cf.feature.name LIKE :search)" +
            " OR EXISTS (SELECT 1 FROM CarSurcharge cc WHERE cc.car = c AND cc.surcharge.name LIKE :search)"
    )
    Page<Car> searchEverything(String search, Pageable pageable);



}
