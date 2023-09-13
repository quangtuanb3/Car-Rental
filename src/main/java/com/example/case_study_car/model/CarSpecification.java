package com.example.case_study_car.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "car_specifications")
@Entity
@Data
@NoArgsConstructor
public class CarSpecification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
//    @JoinColumn(name = "car_id")
    private Car car;


    @ManyToOne
    private Specification specification;

    public CarSpecification(Car car, Specification specification) {
        this.car = car;
        this.specification = specification;
    }
}
