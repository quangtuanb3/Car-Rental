//package com.example.case_study_car.domain;
//
//
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.math.BigDecimal;
//
//@Table(name = "car_surcharges")
//@Entity
//@Getter
//@Setter
//@NoArgsConstructor
//@Builder
//@AllArgsConstructor
//public class CarSurcharge {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//
//    private Long id;
//
//    private BigDecimal priceSurcharge;
//
//    @ManyToOne
//    @JoinColumn(name = "car_id")
//    private Car car;
//
//
//    @ManyToOne
//    @JoinColumn(name = "surcharge_id")
//    private Surcharge surcharge;
//
//    public CarSurcharge(Car car, Surcharge surcharge) {
//        this.car = car;
//        this.surcharge = surcharge;
//    }
//}
