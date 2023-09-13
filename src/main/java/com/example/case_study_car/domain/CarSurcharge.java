package com.example.case_study_car.domain;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Table(name = "car_surcharges")
@Entity
@Data
@NoArgsConstructor
public class CarSurcharge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private BigDecimal priceSurcharge;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;


    @ManyToOne
    @JoinColumn(name = "surcharge_id")
    private Surcharge surcharge;

    public CarSurcharge(Car car, Surcharge surcharge) {
        this.car = car;
        this.surcharge = surcharge;
    }
}
