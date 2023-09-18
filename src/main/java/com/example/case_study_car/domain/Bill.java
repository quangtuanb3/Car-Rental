package com.example.case_study_car.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Table
@Entity
@NoArgsConstructor
@Getter
@Setter
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerName;

    private String customerPhoneNumber;

    private String customerEmail;

    private String customerIdNumber;

    private LocalDateTime pickupTime;

    private String pickupLocation;

    private String dropOffLocation;

    private LocalDateTime expectedDropOffTime;

    private LocalDateTime actualDropOffTime;

    private String deliveryFee;

    private String totalPrice;

    private BigDecimal rentPrice;

    private BigDecimal deposit;

    private BigDecimal excessDistanceFee;

    private BigDecimal overtimeFee;

    private BigDecimal cleaningFee;

    private String tradeCode;



    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne()
    private Car car;
}
