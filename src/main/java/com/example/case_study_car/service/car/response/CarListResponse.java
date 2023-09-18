package com.example.case_study_car.service.car.response;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
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

    private String description;

    private String agency;

    private String specifications;

    private String features;

    private String urlImages;

//    private String surcharges;
}
