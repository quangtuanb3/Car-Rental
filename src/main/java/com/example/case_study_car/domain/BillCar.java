package com.example.case_study_car.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "bill_cars")
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class BillCar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String licensePlate;

    private BigDecimal rentPrice;

    private BigDecimal deliveryPrice;

    private BigDecimal totalPrice;

    private BigDecimal excessDistanceFee;

    private BigDecimal overtimeFee;

    private BigDecimal cleaningFee;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    @ManyToOne
    @JoinColumn(name = "bill_id")
    private Bill bill;


}
