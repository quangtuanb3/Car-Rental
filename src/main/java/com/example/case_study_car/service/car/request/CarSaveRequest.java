package com.example.case_study_car.service.car.request;

import com.example.case_study_car.service.request.SelectOptionRequest;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
public class CarSaveRequest {

    private String name;

    private String status;

    private String licensePlate;

    private String priceHours;

    private String priceDays;

    private String priceDelivery;

    private String description;

    private String excessDistanceFee;

    private String overtimeFee;

    private String cleaningFee;

    private SelectOptionRequest agency;

    private List<String> idSpecifications;

    private List<String> idFeatures;

    private List<String> urlImages;

//    private List<String> idSurcharges;


}
