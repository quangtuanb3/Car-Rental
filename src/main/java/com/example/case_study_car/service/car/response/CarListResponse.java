package com.example.case_study_car.service.car.response;

import com.example.case_study_car.domain.Image;
import com.example.case_study_car.service.request.SelectOptionRequest;
import jakarta.persistence.Column;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarListResponse {

    private Long id;

    private String name;

    private String status;

    private String licensePlate;

    private BigDecimal priceHours;

    private BigDecimal priceDays;

    private BigDecimal priceDelivery;

    private BigDecimal excessDistanceFee;

    private BigDecimal overtimeFee;

    private BigDecimal cleaningFee;

    private String description;

    private String agency;

    private String logo;

    private String specifications;

    private String features;

    private String urlImages;

    private String image;

    private List<String> images;

//    private String surcharges;
}
