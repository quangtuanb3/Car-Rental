package com.example.case_study_car.service.car.response;

import com.example.case_study_car.service.image.ImageResponse;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
public class CarDetailResponse {

    private Long id;

    private String name;

    private String status;

    private String licensePlate;

    private BigDecimal priceHours;

    private BigDecimal priceDays;

    private BigDecimal priceDelivery;

    private String description;

    private Long agencyId;

    private List<Long> specificationIds;

    private List<Long> featureIds;

    private List<String> urlImages;

    private ImageResponse image;

    private List<String> images;


//    private List<Long> surchargeIds;


}
