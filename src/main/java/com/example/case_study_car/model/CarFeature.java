package com.example.case_study_car.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "car_features")
@Entity
@Data
@NoArgsConstructor
public class CarFeature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;


    @ManyToOne
    @JoinColumn(name = "feature_id")
    private Feature feature;

    public CarFeature(Car car, Feature feature) {
        this.car = car;
        this.feature = feature;
    }
}
