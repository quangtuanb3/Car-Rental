package com.example.case_study_car.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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

    private String status;

    private String licensePlate;

    private BigDecimal priceHours;

    private BigDecimal priceDays;

    @Column(columnDefinition = "LONGTEXT")
    private String description;

    @OneToMany(mappedBy = "car")
    private List<Image> images;

    @ManyToOne
    @JoinColumn(name = "agency_id")
    private Agency agency;

    @OneToMany(mappedBy = "car")
    private List<CarSpecification> carSpecifications;

    @OneToMany(mappedBy = "car")
    private List<CarFeature> carFeatures;

    @OneToMany(mappedBy = "car")
    private List<CarSurcharge> carSurcharges;

}
