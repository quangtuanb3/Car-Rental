package com.example.case_study_car.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "features")
@Data
@NoArgsConstructor
public class Feature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;


    @OneToMany(mappedBy = "feature")
    private List<CarFeature> carFeatures;

    public Feature(Long id) {
        this.id = id;
    }

    public Feature(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
