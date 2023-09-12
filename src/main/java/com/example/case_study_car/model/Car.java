package com.example.case_study_car.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "cars")
@Data
@NoArgsConstructor
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private BigDecimal priceHours;

    private BigDecimal priceDays;



    private String description;

    @OneToOne
    private File poster;

    @ManyToOne
    @JoinColumn(name = "agency_id")
    private Agency agency;

    @OneToMany(mappedBy = "car")
    private List<CarCategory> carCategories;

    @OneToMany(mappedBy = "car")
    private List<CarFeature> carFeatures;

    @OneToMany(mappedBy = "car")
    private List<File> files;
}
