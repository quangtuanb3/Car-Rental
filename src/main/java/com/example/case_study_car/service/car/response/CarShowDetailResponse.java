package com.example.case_study_car.service.car.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;


@Data
@NoArgsConstructor
public class CarShowDetailResponse {

    private Long id;

    private String name;

    private String status;

    private String licensePlate;

    private BigDecimal priceHours;

    private BigDecimal priceDays;

    private String description;

    private String agencyName;

    private List<String> specificationNames;

    private List<String> featureNames;

    private List<String> urlImages;

    private List<String> surchargeNames;
}
