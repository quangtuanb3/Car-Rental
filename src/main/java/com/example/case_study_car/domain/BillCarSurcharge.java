//package com.example.case_study_car.domain;
//
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Table(name = "bill_car_surcharges")
//@Entity
//@Data
//@NoArgsConstructor
//@Builder
//@AllArgsConstructor
//public class BillCarSurcharge {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//
//    private Long id;
//
//    @ManyToOne
////    @JoinColumn(name = "surcharge_id")
//    private CarSurcharge carSurcharge;
//
//    @ManyToOne
//    @JoinColumn(name = "bill_id")
//    private Bill bill;
//}
