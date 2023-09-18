package com.example.case_study_car.domain;


import com.example.case_study_car.domain.enumaration.ECarStatus;
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

    @Enumerated(EnumType.STRING)
    private ECarStatus status;

    private String licensePlate;

    private BigDecimal priceHours;

    private BigDecimal priceDays;

    private BigDecimal priceDelivery;

    private BigDecimal excessDistanceFee;

    private BigDecimal overtimeFee;

    private BigDecimal cleaningFee;

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


    public Car(Long id) {
        this.id = id;
    }
}
