package com.example.case_study_car.repository;


import com.example.case_study_car.domain.Agency;
import com.example.case_study_car.domain.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

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

    @Query(value = "SELECT c FROM Car c " +
            "ORDER BY (SELECT COUNT(cf.id) FROM CarFeature cf WHERE cf.car = c) DESC LIMIT 5")
    List<Car> getBestCars();

    @Query(value = "SELECT c FROM Car c " +
            "WHERE (EXISTS (SELECT 1 FROM CarSpecification cs " +
            "               WHERE cs.car = c " +
            "                 AND cs.specification.type = 'SEAT' " +
            "                 AND cs.specification.name = :seat) " +
            "    OR c.agency.name = :agency " +
            "    OR (c.priceDays BETWEEN (:priceDays - 30) AND (:priceDays + 30))) " +
            "    AND c.id <> :id " +
            "ORDER BY (CASE " +
            "   WHEN c.agency.name = :agency THEN 1 " +
            "   ELSE 2 " +
            "END), " +
            "c.priceDays ASC " +
            "LIMIT 3")
    List<Car> getRelatedCars(@Param("agency") String agency,
                             @Param("seat") String seat,
                             @Param("priceDays") BigDecimal priceDays,
                             @Param("id") Long id);


}
