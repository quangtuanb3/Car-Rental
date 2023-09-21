package com.example.case_study_car.service.bill.request;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BillSaveRequest {
    private String customerName;

    private String customerPhoneNumber;

    private String customerEmail;

    private String customerIdNumber;

    private String carId;

    private String pickupTime;

    private String expectedDropOffTime;

    private String pickupLocation;

    private String dropOffLocation;

    private String rentPrice;

    private String deliveryFee;

    private String totalPrice;

    private String deposit;

    private String tradeCode;

    private  String licensePlate;

    private String billStatus;

}
