package com.example.case_study_car.domain;

import com.example.case_study_car.domain.enumaration.EBillStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Table(name = "bills")
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

    private BigDecimal deliveryFee;

    private BigDecimal totalPrice;

    private BigDecimal rentPrice;

    private BigDecimal deposit;

    private BigDecimal excessDistanceFee;

    private BigDecimal overtimeFee;

    private BigDecimal cleaningFee;

    private String tradeCode;

    private String licensePlate;

    @Enumerated(value = EnumType.STRING)
    private EBillStatus billStatus;


    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne()
    private Car car;


}
