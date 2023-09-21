package com.example.case_study_car.service.bill.response;

import com.example.case_study_car.domain.enumaration.EBillStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillListResponse {

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

    private BigDecimal excessDistanceFee;

    private BigDecimal overtimeFee;

    private BigDecimal cleaningFee;

    private BigDecimal deposit;

    private String tradeCode;

    private  String licensePlate;

    private String billStatus;
}
