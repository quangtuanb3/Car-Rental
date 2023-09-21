package com.example.case_study_car.service.bill.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class BillDetailResponse {

    private Long id;

    private String customerName;

    private String customerPhoneNumber;

    private String customerEmail;

    private String customerIdNumber;

    private Long carId;

    private LocalDateTime pickupTime;

    private LocalDateTime expectedDropOffTime;

    private String pickupLocation;

    private String dropOffLocation;

    private BigDecimal rentPrice;

    private BigDecimal deliveryFee;

    private BigDecimal totalPrice;

    private BigDecimal deposit;

    private BigDecimal excessDistanceFee;

    private BigDecimal overtimeFee;

    private BigDecimal cleaningFee;

    private String tradeCode;

    private  String licensePlate;

    private String billStatus;
}
